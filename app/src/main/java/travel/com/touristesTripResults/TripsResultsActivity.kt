package travel.com.touristesTripResults

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v7.widget.LinearLayoutManager

import android.support.v7.widget.RecyclerView
import android.view.View

import java.util.ArrayList

import android.support.v4.widget.SwipeRefreshLayout

import travel.com.R

import android.widget.Toast
import android.os.Handler
import android.support.v7.widget.Toolbar


import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DividerItemDecoration
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_trips_results.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import travel.com.touristesPopUpFilter.PopUPFilter
import travel.com.touristesPopUpFilter.SearchQueryObject
import travel.com.touristesTripDetail.TouristesTripDetailActivity
import travel.com.touristesTripResults.models.DataItem
import travel.com.utility.Constants
import travel.com.utility.EndlessRecyclerViewScrollListener
import travel.com.utility.Util
import travel.com.utility.toast


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
            // Do your stuff on refresh
            Handler().postDelayed({
                if (swipeRefreshRecyclerList!!.isRefreshing)
                    swipeRefreshRecyclerList!!.isRefreshing = false
            }, 5000)
        }

        fab.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PopUPFilter::class.java))
            overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
        })

        // set scroll listener
        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                toast("Loading more...")
            }

        }

        setAdapter()

    }

    private fun findViews() {
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshRecyclerList = findViewById<View>(R.id.swipe_refresh_recycler_list) as SwipeRefreshLayout
    }


    private fun setAdapter() {
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())
        modelList.add(DataItem())

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


}
