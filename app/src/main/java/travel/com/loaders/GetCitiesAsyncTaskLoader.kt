package travel.com.loaders


import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.touristesTripsFilter.CitiesResponse
import travel.com.touristesTripsFilter.CityItem
import travel.com.utility.Util
import java.io.*

/**
 * Created by mostafa_anter on 5/5/17.
 */

class GetCitiesAsyncTaskLoader(context: Context, private val country_id: Int) : AsyncTaskLoader<List<CityItem>>(context) {

    private var mData: List<CityItem>? = null
    private val downloadedFile = File(
            getContext().filesDir, FILE_NAME)

    // for retrofit request
    private var subscription: Subscription? = null
    private val apiService: ApiInterface

    init {
        // get apiService
        apiService = ApiClient.getClient().create(ApiInterface::class.java)
    }

    override fun onStartLoading() {
        if (mData != null) {
            // use cashed data
            deliverResult(mData)
        }
        Util.ReadObjectsFromFile<CityItem>({
            mData = it
            if (mData != null) {
                // use cashed data
                deliverResult(mData)
            }

            forceLoad()
        }, downloadedFile).execute()
    }

    override fun loadInBackground(): List<CityItem>? {
        val getCities = apiService.getCities(BuildConfig.Header_Accept,
                BuildConfig.Header_Authorization, BuildConfig.From,
                BuildConfig.Accept_Language, BuildConfig.User_Agent, country_id)
        subscription = getCities
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<CitiesResponse>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        Log.e("error", e.message)
                    }

                    override fun onNext(getCountriesResult: CitiesResponse) {
                        mData = getCountriesResult.items
                        deliverResult(mData)
                        deleteFileContent(downloadedFile)
                        saveObjectsInsideFile(downloadedFile, mData!!)

                    }
                })


        return null
    }

    override fun deliverResult(data: List<CityItem>?) {
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
        private val FILE_NAME = "GetCitiesAsyncTaskLoader"

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