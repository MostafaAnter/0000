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
import travel.com.store.TravellawyPrefStore
import travel.com.touristesCompanies.TouristesCompanies
import travel.com.touristesCompanies.models.CompaniesResponse
import travel.com.touristesTripResults.models.Member
import travel.com.utility.Constants
import travel.com.utility.Util
import java.io.*

/**
 * Created by mostafa_anter on 5/5/17.
 */

class GetCompaniesNextAsyncTaskLoader(context: Context, path: String) : AsyncTaskLoader<List<Member>>(context) {

    private var mData: List<Member>? = null
    // for retrofit request
    private var subscription: Subscription? = null
    private val apiService: ApiInterface
    private var store: TravellawyPrefStore

    init {
        // get apiService
        apiService = ApiClient.getClient(path)!!.create(ApiInterface::class.java)
        store = TravellawyPrefStore(context)
    }

    override fun onStartLoading() {
            forceLoad()
    }

    override fun loadInBackground(): List<Member>? {
        val getCategories = apiService.getNextCompanies(BuildConfig.Header_Accept,
                store.getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language,
                BuildConfig.User_Agent)
        subscription = getCategories
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<CompaniesResponse>() {
                    override fun onCompleted() {
                        Log.e("", "")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("", e.message)
                    }

                    override fun onNext(getCountriesResult: CompaniesResponse) {
                        mData = getCountriesResult.paginator?.data

                        if (getCountriesResult.paginator.next_page_url != null) {
                            TouristesCompanies.nextUrl = getCountriesResult.paginator.next_page_url
                        }
                        deliverResult(mData)
                    }
                })


        return null
    }

    override fun deliverResult(data: List<Member>?) {
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