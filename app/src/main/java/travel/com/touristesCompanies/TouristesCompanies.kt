package travel.com.touristesCompanies

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_touristes_companies.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util
import java.util.*


class TouristesCompanies : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var swipeRefreshRecyclerList: SwipeRefreshLayout? = null
    private var mAdapter: RecyclerViewAdapter? = null

    private val modelList = ArrayList<CompanyModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touristes_companies)

        findViews()
        changeViewsFonts()
        setToolbar()
        setAdapter()

        swipeRefreshRecyclerList!!.setOnRefreshListener {
            // Do your stuff on refresh
            Handler().postDelayed({
                if (swipeRefreshRecyclerList!!.isRefreshing)
                    swipeRefreshRecyclerList!!.isRefreshing = false
            }, 5000)
        }

    }

    private fun findViews() {
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshRecyclerList = findViewById<View>(R.id.swipe_refresh_recycler_list) as SwipeRefreshLayout
    }

    private fun setAdapter() {


        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))
        modelList.add(CompanyModel("شركة أيجبت فور ترافلز", "عدد الرحلات 500"))



        mAdapter = RecyclerViewAdapter(this@TouristesCompanies, modelList)

        recyclerView!!.setHasFixedSize(true)

        // use a linear layout manager

        val layoutManager = LinearLayoutManager(this)

        recyclerView!!.layoutManager = layoutManager


        val dividerItemDecoration = DividerItemDecoration(recyclerView!!.context, layoutManager.orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this@TouristesCompanies, R.drawable.divider_recyclerview)!!)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        recyclerView!!.adapter = mAdapter


        mAdapter!!.SetOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, model: CompanyModel) {

                //handle item click events here
                Toast.makeText(this@TouristesCompanies, "Hey " + model.title, Toast.LENGTH_SHORT).show()


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


}
