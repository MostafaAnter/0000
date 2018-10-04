package travel.com.touristesCompanies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v4.app.LoaderManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.Loader
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_touristes_companies.*
import travel.com.R
import travel.com.loaders.GetCompaniesAsyncTaskLoader
import travel.com.loaders.GetCompaniesNextAsyncTaskLoader
import travel.com.touristesCompaniesDetails.CompaniesDetailActivity
import travel.com.touristesTripResults.models.Member
import travel.com.utility.*
import java.util.*


class TouristesCompanies : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var swipeRefreshRecyclerList: SwipeRefreshLayout? = null
    private var mAdapter: RecyclerViewAdapter? = null

    private val modelList = ArrayList<Member>()
    val layoutManager = LinearLayoutManager(this)

    // for load more data
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    companion object {
        var nextUrl: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touristes_companies)
        findViews()
        changeViewsFonts()
        setToolbar()

        swipeRefreshRecyclerList!!.setOnRefreshListener {
            getData()
        }

        // set scroll listener
        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                toast("جارى تحميل المزيد...")
                getNextData()
            }

        }

        setAdapter()

        if (Util.isOnline(this)){
            getData()
        }else{
            SweetDialogHelper(this).showErrorMessage("فشل", "لايوجد انترنت!")
        }

    }

    private fun getData(){
        if (!swipeRefreshRecyclerList!!.isRefreshing)
            swipeRefreshRecyclerList!!.isRefreshing = true
        supportLoaderManager.initLoader(Random().nextInt(1000 - 10 + 1) + 10, null, getCompanies)

    }

    private fun getNextData(){
        supportLoaderManager.initLoader(Random().nextInt(1000 - 10 + 1) + 10, null, getNextCompanies)

    }

    private fun findViews() {
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshRecyclerList = findViewById<View>(R.id.swipe_refresh_recycler_list) as SwipeRefreshLayout
    }

    private fun setAdapter() {
        mAdapter = RecyclerViewAdapter(this@TouristesCompanies, modelList)

        recyclerView!!.setHasFixedSize(true)

        // use a linear layout manager

        recyclerView!!.layoutManager = layoutManager


        val dividerItemDecoration = DividerItemDecoration(recyclerView!!.context, layoutManager.orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this@TouristesCompanies, R.drawable.divider_recyclerview)!!)
        recyclerView!!.addItemDecoration(dividerItemDecoration)
        recyclerView!!.addOnScrollListener(scrollListener)

        recyclerView!!.adapter = mAdapter


        mAdapter!!.SetOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, model: Member) {

                val intent = Intent(this@TouristesCompanies, CompaniesDetailActivity::class.java)
                intent.putExtra("item", modelList[position] as Parcelable)
                startActivity(intent)


            }
        })


    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@TouristesCompanies, Constants.FONT_REGULAR, toolbarTitle)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@TouristesCompanies, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    // get countries
    private val getCompanies = object : LoaderManager.LoaderCallbacks<List<Member>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<Member>> {
            return GetCompaniesAsyncTaskLoader(this@TouristesCompanies)
        }

        override fun onLoadFinished(
                loader: Loader<List<Member>>, data: List<Member>?) {
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

        override fun onLoaderReset(loader: Loader<List<Member>>) {
            // Loader reset, throw away our data,
            // unregister any listeners, etc.
            // Of course, unless you use destroyLoader(),
            // this is called when everything is already dying
            // so a completely empty onLoaderReset() is
            // totally acceptable
        }
    }

    private val getNextCompanies = object : LoaderManager.LoaderCallbacks<List<Member>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<Member>> {
            return GetCompaniesNextAsyncTaskLoader(this@TouristesCompanies, nextUrl)
        }

        override fun onLoadFinished(
                loader: Loader<List<Member>>, data: List<Member>?) {
            // Display our data, for instance updating our adapter
            if (data != null && data.isNotEmpty()) {
                with(modelList){
                    data.forEach{
                        add(it)
                    }
                }
                mAdapter?.notifyDataSetChanged()
            }
        }

        override fun onLoaderReset(loader: Loader<List<Member>>) {
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
