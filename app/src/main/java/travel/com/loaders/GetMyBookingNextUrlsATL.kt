package travel.com.loaders


import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.myBookings.MyBookingsActivity
import travel.com.myBookings.models.DataItem
import travel.com.myBookings.models.MyReservationsResponse
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.store.TravellawyPrefStore
import travel.com.utility.Constants
import travel.com.utility.Util
import java.io.*

/**
 * Created by mostafa_anter on 5/5/17.
 */

class GetMyBookingNextUrlsATL(context: Context, private val path: String = "") : AsyncTaskLoader<List<DataItem>>(context) {

    private var mData: List<DataItem>? = null

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

    override fun loadInBackground(): List<DataItem>? {
        val getCategories = apiService.getNextReservations(BuildConfig.Header_Accept,
                store.getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language,
                BuildConfig.User_Agent)
        subscription = getCategories
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<MyReservationsResponse>() {
                    override fun onCompleted() {
                        Log.e("", "")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("", e.message)
                    }

                    override fun onNext(getCountriesResult: MyReservationsResponse) {
                        mData = getCountriesResult.paginator?.data

                        if (getCountriesResult.paginator.next_page_url != null) {
                            MyBookingsActivity.nextUrl = getCountriesResult.paginator.next_page_url
                        }
                        deliverResult(mData)

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
}