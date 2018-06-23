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
        Util.changeViewTypeFace(this@TouristesTripDetailActivity, Constants.FONT_REGULAR, toolbarTitle)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@TouristesTripDetailActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    private fun setServicesAdapter() {


        servicesList.add(ServisesModel("Android", "Hello " + " Android"))
        servicesList.add(ServisesModel("Beta", "Hello " + " Beta"))
        servicesList.add(ServisesModel("Cupcake", "Hello " + " Cupcake"))
        servicesList.add(ServisesModel("Donut", "Hello " + " Donut"))
        servicesList.add(ServisesModel("Eclair", "Hello " + " Eclair"))
        servicesList.add(ServisesModel("Froyo", "Hello " + " Froyo"))
        servicesList.add(ServisesModel("Gingerbread", "Hello " + " Gingerbread"))
        servicesList.add(ServisesModel("Honeycomb", "Hello " + " Honeycomb"))
        servicesList.add(ServisesModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"))
        servicesList.add(ServisesModel("Jelly Bean", "Hello " + " Jelly Bean"))
        servicesList.add(ServisesModel("KitKat", "Hello " + " KitKat"))
        servicesList.add(ServisesModel("Lollipop", "Hello " + " Lollipop"))
        servicesList.add(ServisesModel("Marshmallow", "Hello " + " Marshmallow"))
        servicesList.add(ServisesModel("Nougat", "Hello " + " Nougat"))
        servicesList.add(ServisesModel("Android O", "Hello " + " Android O"))


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


        commentsList.add(CommentsModel("Android", "Hello " + " Android"))
        commentsList.add(CommentsModel("Beta", "Hello " + " Beta"))
        commentsList.add(CommentsModel("Cupcake", "Hello " + " Cupcake"))
        commentsList.add(CommentsModel("Donut", "Hello " + " Donut"))
        commentsList.add(CommentsModel("Eclair", "Hello " + " Eclair"))
        commentsList.add(CommentsModel("Froyo", "Hello " + " Froyo"))
        commentsList.add(CommentsModel("Gingerbread", "Hello " + " Gingerbread"))
        commentsList.add(CommentsModel("Honeycomb", "Hello " + " Honeycomb"))
        commentsList.add(CommentsModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"))
        commentsList.add(CommentsModel("Jelly Bean", "Hello " + " Jelly Bean"))
        commentsList.add(CommentsModel("KitKat", "Hello " + " KitKat"))
        commentsList.add(CommentsModel("Lollipop", "Hello " + " Lollipop"))
        commentsList.add(CommentsModel("Marshmallow", "Hello " + " Marshmallow"))
        commentsList.add(CommentsModel("Nougat", "Hello " + " Nougat"))
        commentsList.add(CommentsModel("Android O", "Hello " + " Android O"))


        commentsAdapter = CommentsAdapter(this@TouristesTripDetailActivity, commentsList)

        recyclerView2.setHasFixedSize(true)

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
