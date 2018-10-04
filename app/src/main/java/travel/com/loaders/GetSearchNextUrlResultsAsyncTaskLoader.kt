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
import travel.com.touristesTripResults.TripsResultsActivity
import travel.com.touristesTripResults.models.DataItem
import travel.com.touristesTripResults.models.SearchResults
import travel.com.touristesTripsFilter.CountriesResponse
import travel.com.touristesTripsFilter.CountryItem
import travel.com.touristesTripsFilter.models.CategoryItem
import travel.com.touristesTripsFilter.models.TripCategoriesResponse
import travel.com.utility.Constants
import travel.com.utility.Util

/**
 * Created by mostafa_anter on 5/5/17.
 */

class GetSearchNextUrlResultsAsyncTaskLoader(context: Context, baseUrl: String) : AsyncTaskLoader<List<DataItem>>(context) {

    private var mData: List<DataItem>? = null
    // for retrofit request
    private var subscription: Subscription? = null
    private val apiService: ApiInterface
    private var store: TravellawyPrefStore

    init {
        // get apiService
        apiService = ApiClient.getClient(baseUrl)!!.create(ApiInterface::class.java)
        store = TravellawyPrefStore(context)
    }

    override fun onStartLoading() {
            forceLoad()
    }

    override fun loadInBackground(): List<DataItem>? {
        val getCategories = apiService.searchNextUrl(BuildConfig.Header_Accept,
                store.getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language,
                BuildConfig.User_Agent)
        subscription = getCategories
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<SearchResults>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(getCountriesResult: SearchResults) {
                        mData = getCountriesResult.paginator?.data
                        TripsResultsActivity.nextUrl = getCountriesResult.paginator?.next_page_url!!
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