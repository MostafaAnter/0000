package travel.com.touristesTripDetail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_touristes_trip_detail.*
import kotlinx.android.synthetic.main.content_touristes_trip_detail.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util
import java.util.*

class TouristesTripDetailActivity : AppCompatActivity() {

    // for services avilable
    private var servicesAdapter: ServicesAvilableAdapter? = null
    private val servicesList = ArrayList<ServisesModel>()

    // for comments
    private var commentsAdapter: CommentsAdapter? = null
    private val commentsList = ArrayList<CommentsModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touristes_trip_detail)

        changeViewsFonts()
        setToolbar()

        setCommentsAdapter()
        setServicesAdapter()
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@TouristesTripDetailActivity, Constants.FONT_REGULAR, toolbarTitle,
                text1, text2, text3, text4, ratingValue, text6, text7, text8, text9,
                text10, text11, text12, text13, button1, button2, button3, button4, button5, button6)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@TouristesTripDetailActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    private fun setServicesAdapter() {


        servicesList.add(ServisesModel("الطعام والشراب", " فطار + العشاء شامل الضريبه"))
        servicesList.add(ServisesModel("الطعام والشراب", " فطار + العشاء شامل الضريبه"))
        servicesList.add(ServisesModel("الطعام والشراب", " فطار + العشاء شامل الضريبه"))
        servicesList.add(ServisesModel("الطعام والشراب", " فطار + العشاء شامل الضريبه"))

        servicesAdapter = ServicesAvilableAdapter(this@TouristesTripDetailActivity, servicesList)

        recyclerView1.setHasFixedSize(true)
        recyclerView1.isNestedScrollingEnabled = false

        // use a linear layout manager

        val layoutManager = LinearLayoutManager(this)

        recyclerView1.setLayoutManager(layoutManager)

        recyclerView1.setAdapter(servicesAdapter)


        servicesAdapter!!.SetOnItemClickListener(object : ServicesAvilableAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, model: ServisesModel) {

                //handle item click events here
                Toast.makeText(this@TouristesTripDetailActivity, "Hey " + model.title, Toast.LENGTH_SHORT).show()


            }
        })


    }
    private fun setCommentsAdapter() {


        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))
        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))
        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))
        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))
        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))
        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))
        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))
        commentsList.add(CommentsModel("مصطفى عنتر", " المكان جميل والعاملين زى الفل"))

        commentsAdapter = CommentsAdapter(this@TouristesTripDetailActivity, commentsList)

        recyclerView2.setHasFixedSize(true)
        recyclerView2.isNestedScrollingEnabled = false

        // use a linear layout manager

        val layoutManager = LinearLayoutManager(this)

        recyclerView2.setLayoutManager(layoutManager)


        val dividerItemDecoration = DividerItemDecoration(recyclerView2.getContext(), layoutManager.orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this@TouristesTripDetailActivity, R.drawable.divider_recyclerview)!!)
        recyclerView2.addItemDecoration(dividerItemDecoration)

        recyclerView2.setAdapter(commentsAdapter)


        commentsAdapter!!.SetOnItemClickListener(object : CommentsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, model: CommentsModel) {

                //handle item click events here
                Toast.makeText(this@TouristesTripDetailActivity, "Hey " + model.title, Toast.LENGTH_SHORT).show()


            }
        })


    }

}
