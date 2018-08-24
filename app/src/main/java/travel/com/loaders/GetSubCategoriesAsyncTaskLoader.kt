package travel.com.loaders

import android.content.Context
import android.support.v4.content.AsyncTaskLoader

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.io.PrintWriter


import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.store.TravellawyPrefStore
import travel.com.touristesTripsFilter.CountriesResponse
import travel.com.touristesTripsFilter.CountryItem
import travel.com.touristesTripsFilter.models.CategoryItem
import travel.com.touristesTripsFilter.models.TripCategoriesResponse
import travel.com.touristesTripsFilter.models.TripSubCategoryResponse
import travel.com.utility.Constants
import travel.com.utility.Util

/**
 * Created by mostafa_anter on 5/5/17.
 */

class GetSubCategoriesAsyncTaskLoader(context: Context, private val category_id: String) : AsyncTaskLoader<List<CategoryItem>>(context) {

    private var mData: List<CategoryItem>? = null
    private val downloadedFile = File(
            getContext().filesDir, FILE_NAME)

    // for retrofit request
    private var subscription: Subscription? = null
    private val apiService: ApiInterface
    private var store: TravellawyPrefStore

    init {
        // get apiService
        apiService = ApiClient.getClient().create(ApiInterface::class.java)
        store = TravellawyPrefStore(context)
    }

    override fun onStartLoading() {
        if (mData != null) {
            // use cashed data
            deliverResult(mData)
        }
        Util.ReadObjectsFromFile<CategoryItem>({
            mData = it
            if (mData != null) {
                // use cashed data
                deliverResult(mData)
            }

            forceLoad()
        }, downloadedFile).execute()
    }

    override fun loadInBackground(): List<CategoryItem>? {
        val getSubCategories = apiService.getTripsSubCategories(BuildConfig.Header_Accept,
                store.getPreferenceValue(Constants.AUTHORIZATION, "empty"), BuildConfig.From,
                BuildConfig.Accept_Language, BuildConfig.User_Agent, category_id)
        subscription = getSubCategories
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<TripSubCategoryResponse>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(getCountriesResult: TripSubCategoryResponse) {
                        mData = getCountriesResult.items
                        deliverResult(mData)
                        deleteFileContent(downloadedFile)
                        saveObjectsInsideFile(downloadedFile, mData!!)

                    }
                })


        return null
    }

    override fun deliverResult(data: List<CategoryItem>?) {
        // We’ll save the data for later retrieval
        mData = data
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data)
    }

    override fun onReset() {

    }

    private fun <T> saveObjectsInsideFile(file: File, items: List<T>) {
        try {
            val outStream = FileOutputStream(file)
            val objectOutStream = ObjectOutputStream(outStream)
            objectOutStream.writeInt(items.size) // Save size first
            for (item in items)
                objectOutStream.writeObject(item)
            objectOutStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    companion object {
        private val FILE_NAME = "GetSubCategoriesAsyncTaskLoader"

        private fun deleteFileContent(file: File) {
            try {
                val writer = PrintWriter(file)
                writer.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }
    }
}