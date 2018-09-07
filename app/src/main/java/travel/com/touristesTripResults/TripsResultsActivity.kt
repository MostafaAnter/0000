package travel.com.touristesTripResults


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_trips_results.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import travel.com.R
import travel.com.loaders.GetSearchResultsAsyncTaskLoader
import travel.com.touristesPopUpFilter.PopUPFilter
import travel.com.touristesPopUpFilter.SearchQueryObject
import travel.com.touristesTripDetail.TouristesTripDetailActivity
import travel.com.touristesTripResults.models.DataItem
import travel.com.utility.Constants
import travel.com.utility.EndlessRecyclerViewScrollListener
import travel.com.utility.Util
import travel.com.utility.toast
import java.util.*


class TripsResultsActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    private var swipeRefreshRecyclerList: SwipeRefreshLayout? = null
    private var mAdapter: RecyclerViewAdapter? = null

    private val modelList = ArrayList<DataItem>()
    val layoutManager = LinearLayoutManager(this)

    // vars for search
    var date: String? = null
    var region: String? = null
    var country_id: String? = null
    var city_id: String? = null
    var category_id: String? = null
    var subCategory_id: String? = null
    var priceFrom: String? = null
    var priceTo: String? = null

    // for load more data
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    companion object {
        var nextUrl: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips_results)

        // ButterKnife.bind(this);
        findViews()
        changeViewsFonts()
        setToolbar()

        date = intent.extras.getString("date", null)
        region = intent.extras.getString("region", null)
        country_id = intent.extras.getString("country_id", null)
        city_id = intent.extras.getString("city_id", null)

        swipeRefreshRecyclerList!!.setOnRefreshListener {
            getDate()
        }

        fab.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PopUPFilter::class.java))
            overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
        })

        // set scroll listener
        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                toast("جارى تحميل المزيد...")
            }

        }

        setAdapter()

        getDate()


    }

    private fun getDate(){
        if (!swipeRefreshRecyclerList!!.isRefreshing)
            swipeRefreshRecyclerList!!.isRefreshing = true
        supportLoaderManager.initLoader(Random().nextInt(1000 - 10 + 1) + 10, null, getAllTrip)

    }

    private fun findViews() {
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshRecyclerList = findViewById<View>(R.id.swipe_refresh_recycler_list) as SwipeRefreshLayout
    }


    private fun setAdapter() {
        mAdapter = RecyclerViewAdapter(this@TripsResultsActivity, modelList)

        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // use a linear layout manager

        recyclerView!!.layoutManager = layoutManager


        recyclerView!!.adapter = mAdapter

        recyclerView!!.addOnScrollListener(scrollListener)

        mAdapter!!.SetOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, model: DataItem) {
                startActivity(Intent(this@TripsResultsActivity, TouristesTripDetailActivity::class.java))
            }
        })


    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@TripsResultsActivity, Constants.FONT_REGULAR, toolbarTitle)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@TripsResultsActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SearchQueryObject) {
        category_id = event.category_id
        subCategory_id = event.subCategory_id
        priceFrom = event.priceFrom
        priceTo = event.priceTo


    }

    // get countries
    private val getAllTrip = object : LoaderManager.LoaderCallbacks<List<DataItem>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<DataItem>> {
            return GetSearchResultsAsyncTaskLoader(this@TripsResultsActivity)
        }

        override fun onLoadFinished(
                loader: Loader<List<DataItem>>, data: List<DataItem>?) {
            // Display our data, for instance updating our adapter
            if (data != null && data.isNotEmpty()) {
                with(modelList){
                    clear()
                    data.forEach{
                        add(it)
                    }
                }
                mAdapter?.notifyDataSetChanged()
                isEmptyData()
            }
        }

        override fun onLoaderReset(loader: Loader<List<DataItem>>) {
            // Loader reset, throw away our data,
            // unregister any listeners, etc.
            // Of course, unless you use destroyLoader(),
            // this is called when everything is already dying
            // so a completely empty onLoaderReset() is
            // totally acceptable
        }
    }

    fun isEmptyData(){
        if (modelList.size != 0){
            noData.visibility = View.GONE
        }else{
            noData.visibility = View.VISIBLE
        }

        if (swipeRefreshRecyclerList!!.isRefreshing)
            swipeRefreshRecyclerList!!.isRefreshing = false
    }


}
