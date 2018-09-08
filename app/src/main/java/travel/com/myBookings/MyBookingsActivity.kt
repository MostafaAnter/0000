package travel.com.myBookings

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_my_bookings.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import travel.com.R
import travel.com.loaders.GetMyBookingsAsyncTaskLoader
import travel.com.myBookingFilter.MyBookingPopUPFilter
import travel.com.myBookingFilter.StatusObject
import travel.com.myBookings.models.DataItem
import travel.com.myBookingsDetails.BookingDetailsActivity
import travel.com.utility.*
import java.util.*


class MyBookingsActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var swipeRefreshRecyclerList: SwipeRefreshLayout? = null
    private var mAdapter: RecyclerViewAdapter? = null
    private val modelList = ArrayList<DataItem>()

    // for load more data
    val layoutManager = LinearLayoutManager(this)
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    // vars for search
    var statu: String = "."

    companion object {
        var nextUrl: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)
        findViews()
        changeViewsFonts()
        setToolbar()

        swipeRefreshRecyclerList!!.setOnRefreshListener {
            getDate()
        }

        fab.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MyBookingPopUPFilter::class.java))
            overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
        })

        // set scroll listener
        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                toast("جارى تحميل المزيد...")
            }

        }

        setAdapter()

        if (Util.isOnline(this)){
            getDate()
        }else{
            SweetDialogHelper(this).showErrorMessage("فشل", "لايوجد انترنت!")
        }

    }

    private fun getDate(){
        if (!swipeRefreshRecyclerList!!.isRefreshing)
            swipeRefreshRecyclerList!!.isRefreshing = true
        supportLoaderManager.initLoader(Random().nextInt(1000 - 10 + 1) + 10, null, getAllReservetions)

    }

    private fun findViews() {
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshRecyclerList = findViewById<View>(R.id.swipe_refresh_recycler_list) as SwipeRefreshLayout
    }

    private fun setAdapter() {
        mAdapter = RecyclerViewAdapter(this@MyBookingsActivity, modelList)

        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.addOnScrollListener(scrollListener)

        // use a linear layout manager

        recyclerView!!.layoutManager = layoutManager


        recyclerView!!.adapter = mAdapter


        mAdapter!!.SetOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener{
            override  fun onItemClick(view: View, position: Int, model: DataItem) {
                startActivity(Intent(this@MyBookingsActivity, BookingDetailsActivity::class.java))
            }
        })


    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@MyBookingsActivity, Constants.FONT_REGULAR, toolbarTitle)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@MyBookingsActivity, toolbar,
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
    fun onMessageEvent(event: StatusObject) {
        statu = event.statusValue
        getDate()
    }

    // get countries
    private val getAllReservetions = object : LoaderManager.LoaderCallbacks<List<DataItem>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<DataItem>> {
            return GetMyBookingsAsyncTaskLoader(this@MyBookingsActivity, statu)
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
