package travel.com.myBookings

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_my_bookings.*
import travel.com.R
import travel.com.myBookingsDetails.BookingDetailsActivity
import travel.com.utility.Constants
import travel.com.utility.EndlessRecyclerViewScrollListener
import travel.com.utility.Util
import travel.com.utility.toast
import java.util.*


class MyBookingsActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var swipeRefreshRecyclerList: SwipeRefreshLayout? = null
    private var mAdapter: RecyclerViewAdapter? = null
    private val modelList = ArrayList<MyBookingsModel>()

    // for load more data
    val layoutManager = LinearLayoutManager(this)
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)
        findViews()
        changeViewsFonts()
        setToolbar()

        swipeRefreshRecyclerList!!.setOnRefreshListener {
            // Do your stuff on refresh
            Handler().postDelayed({
                if (swipeRefreshRecyclerList!!.isRefreshing)
                    swipeRefreshRecyclerList!!.isRefreshing = false
            }, 5000)
        }

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


        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))
        modelList.add(MyBookingsModel("منتجع شرم كليف", " ايام / 3 ليالي"))

        mAdapter = RecyclerViewAdapter(this@MyBookingsActivity, modelList)

        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.addOnScrollListener(scrollListener)

        // use a linear layout manager

        recyclerView!!.layoutManager = layoutManager


        recyclerView!!.adapter = mAdapter


        mAdapter!!.SetOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener{
            override  fun onItemClick(view: View, position: Int, model: MyBookingsModel) {
                startActivity(Intent(this@MyBookingsActivity, BookingDetailsActivity::class.java))
            }
        })


    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@MyBookingsActivity, Constants.FONT_REGULAR, toolbarTitle,
                button1, button2)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@MyBookingsActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }



}
