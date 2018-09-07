package travel.com.loaders


import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.store.TravellawyPrefStore
import travel.com.touristesCompanies.TouristesCompanies
import travel.com.touristesCompanies.models.CompaniesResponse
import travel.com.touristesCompanies.models.DataItem
import travel.com.utility.Constants
import travel.com.utility.Util
import java.io.*

/**
 * Created by mostafa_anter on 5/5/17.
 */

class GetCompaniesAsyncTaskLoader(context: Context) : AsyncTaskLoader<List<DataItem>>(context) {

    private var mData: List<DataItem>? = null
    private val downloadedFile = File(
            getContext().filesDir, FILE_NAME)

    // for retrofit request
    private var subscription: Subscription? = null
    private val apiService: ApiInterface
    private var store: TravellawyPrefStore

    init {
        // get apiService
        apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        store = TravellawyPrefStore(context)
    }

    override fun onStartLoading() {
        if (mData != null) {
            // use cashed data
            deliverResult(mData)
        }
        Util.ReadObjectsFromFile<DataItem>({
            mData = it
            if (mData != null) {
                // use cashed data
                deliverResult(mData)
            }

            forceLoad()
        }, downloadedFile).execute()
    }

    override fun loadInBackground(): List<DataItem>? {
        val getCategories = apiService.getCompanies(BuildConfig.Header_Accept,
                store.getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language,
                BuildConfig.User_Agent)
        subscription = getCategories
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<CompaniesResponse>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(getCountriesResult: CompaniesResponse) {
                        mData = getCountriesResult.paginator?.data
                        TouristesCompanies.nextUrl = getCountriesResult?.paginator?.next_page_url!!
                        deliverResult(mData)
                        deleteFileContent(downloadedFile)
                        saveObjectsInsideFile(downloadedFile, mData!!)

                    }
                })


        return null
    }

    override fun deliverResult(data: List<DataItem>?) {
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
        private val FILE_NAME = "GetCompaniesAsyncTaskLoader"

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