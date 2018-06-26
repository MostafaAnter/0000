package travel.com.touristesCompaniesDetails

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_companies_detail.*
import kotlinx.android.synthetic.main.content_companies_detail.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util
import java.util.ArrayList

class CompaniesDetailActivity : AppCompatActivity() {

    // for comments
    private var commentsAdapter: CommentsAdapter? = null
    private val commentsList = ArrayList<CommentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companies_detail)
        setToolbar()
        changeViewsFonts()
        setCommentsAdapter()

    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@CompaniesDetailActivity, Constants.FONT_REGULAR, toolbarTitle,
                text1, text2, ratingValue, button1, button2, button3, button4)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@CompaniesDetailActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
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

        commentsAdapter = CommentsAdapter(this@CompaniesDetailActivity, commentsList)

        recyclerView2.setHasFixedSize(true)
        recyclerView2.isNestedScrollingEnabled = false

        // use a linear layout manager

        val layoutManager = LinearLayoutManager(this)

        recyclerView2.setLayoutManager(layoutManager)


        val dividerItemDecoration = DividerItemDecoration(recyclerView2.getContext(), layoutManager.orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this@CompaniesDetailActivity, R.drawable.divider_recyclerview)!!)
        recyclerView2.addItemDecoration(dividerItemDecoration)

        recyclerView2.setAdapter(commentsAdapter)


        commentsAdapter!!.SetOnItemClickListener(object : CommentsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, model: CommentsModel) {

                //handle item click events here
                Toast.makeText(this@CompaniesDetailActivity, "Hey " + model.title, Toast.LENGTH_SHORT).show()


            }
        })


    }

}
