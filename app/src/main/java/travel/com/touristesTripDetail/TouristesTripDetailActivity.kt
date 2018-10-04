package travel.com.touristesTripDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_touristes_trip_detail.*
import kotlinx.android.synthetic.main.content_touristes_trip_detail.*
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.R
import travel.com.bookTrip.BookTripActivity
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.signIn.SignInActivity
import travel.com.store.TravellawyPrefStore
import travel.com.touristesCompaniesDetails.CompaniesDetailActivity
import travel.com.touristesTripDetail.models.TripCommentResponse
import travel.com.touristesTripResults.models.DataItem
import travel.com.utility.Constants
import travel.com.utility.SweetDialogHelper
import travel.com.utility.Util
import java.util.*

class TouristesTripDetailActivity : AppCompatActivity(), View.OnClickListener{

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button1 -> {
                startActivity(Intent(this@TouristesTripDetailActivity, CompaniesDetailActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("item", tripItem.member as Parcelable))
            }
            R.id.button6, R.id.button7 -> {

                if (TravellawyPrefStore(this).getPreferenceValue(Constants.AUTHORIZATION, "empty")
                        !!.contentEquals("empty")) {
                    startActivity(Intent(this@TouristesTripDetailActivity, SignInActivity::class.java))
                    overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
                }else{
                    startActivity(Intent(this@TouristesTripDetailActivity, BookTripActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .putExtra("item", tripItem as Parcelable))
                }
            }
        }
    }

    // for services avilable
    private var servicesAdapter: ServicesAvilableAdapter? = null
    private val servicesList = ArrayList<ServisesModel>()

    // for comments
    private var commentsAdapter: CommentsAdapter? = null
    private val commentsList = ArrayList<CommentsModel>()

    private var apiService: ApiInterface? = null
    private var subscription1: Subscription? = null


    private lateinit var tripItem: DataItem

    private lateinit var sweetDialogHelper: SweetDialogHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touristes_trip_detail)

        changeViewsFonts()
        setToolbar()

        setCommentsAdapter()
        setServicesAdapter()

        button1.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener (this)


        tripItem = intent.getParcelableExtra("item")

        sweetDialogHelper = SweetDialogHelper(this)
        // init api service
        apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        bindData()

    }

    fun changeViewsFonts() {
        Util.changeViewTypeFace(this@TouristesTripDetailActivity, Constants.FONT_REGULAR, toolbarTitle,
                text1, text2, text3, text4, ratingValueText, text6, text7, text8, text9,
                text10, text11, text12, text13, button1, button2, button3, button4, button5, button6)
    }

    fun setToolbar() {
        Util.manipulateToolbar(this@TouristesTripDetailActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    private fun setServicesAdapter() {
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



            }
        })


    }

    private fun bindData(){
        with(tripItem){
            text2.text = member?.name
            text3.text = title

            val dayNum = Util.printDifference(Util.conVertDateTextToObject(start_date?: ""),
                    Util.conVertDateTextToObject(end_date?: ""))

            text4.text = "$dayNum" + " أيام" + " / " + "${dayNum - 1}" + " ليالى"
            ratingBar.rating = stars!!.toFloat()

            ratingValueText.text =  stars.toFloat().toString()
            text6.text = "from $start_date to $end_date"

            text7.text = price + "EL"

            when{
                trip_images?.size == 1 ->{
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(smallImage1)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(mainImage)
                    smallImage1.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[0].image) // add your image url
                                .into(mainImage)
                    }

                }
                trip_images?.size == 2 ->{
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(smallImage1)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[1].image) // add your image url
                            .into(smallImage2)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(mainImage)

                    smallImage1.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[0].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage2.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[1].image) // add your image url
                                .into(mainImage)
                    }
                }
                trip_images?.size == 3 ->{

                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(smallImage1)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[1].image) // add your image url
                            .into(smallImage2)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[2].image) // add your image url
                            .into(smallImage3)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(mainImage)
                    smallImage1.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[0].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage2.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[1].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage3.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[2].image) // add your image url
                                .into(mainImage)
                    }
                }
                trip_images?.size == 4 ->{
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(smallImage1)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[1].image) // add your image url
                            .into(smallImage2)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[2].image) // add your image url
                            .into(smallImage3)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[3].image) // add your image url
                            .into(smallImage4)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(mainImage)
                    smallImage1.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[0].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage2.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[1].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage3.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[2].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage4.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[3].image) // add your image url
                                .into(mainImage)
                    }
                }
                trip_images?.size == 5 ->{
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(smallImage1)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[1].image) // add your image url
                            .into(smallImage2)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[2].image) // add your image url
                            .into(smallImage3)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[3].image) // add your image url
                            .into(smallImage4)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[4].image) // add your image url
                            .into(smallImage5)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(mainImage)
                    smallImage1.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[0].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage2.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[1].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage3.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[2].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage4.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[3].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage5.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[4].image) // add your image url
                                .into(mainImage)
                    }
                }
                trip_images?.size!! >= 6 ->{
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(smallImage1)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[1].image) // add your image url
                            .into(smallImage2)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[2].image) // add your image url
                            .into(smallImage3)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[3].image) // add your image url
                            .into(smallImage4)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[4].image) // add your image url
                            .into(smallImage5)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[5].image) // add your image url
                            .into(smallImage6)
                    Glide.with(this@TouristesTripDetailActivity)   // pass Context
                            .load(trip_images[0].image) // add your image url
                            .into(mainImage)
                    smallImage1.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[0].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage2.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[1].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage3.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[2].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage4.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[3].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage5.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[4].image) // add your image url
                                .into(mainImage)
                    }
                    smallImage6.setOnClickListener {
                        Glide.with(this@TouristesTripDetailActivity)   // pass Context
                                .load(trip_images[5].image) // add your image url
                                .into(mainImage)
                    }
                }
                else ->{

                }
            }

            text9.text = hotel_dec
            text12.text = important_info
            button2.setOnClickListener{
                button2.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                button3.setBackgroundColor(resources.getColor(R.color.gray_88333333))
                button4.setBackgroundColor(resources.getColor(R.color.gray_88333333))
                text9.text = hotel_dec
            }
            button3.setOnClickListener{
                button3.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                button2.setBackgroundColor(resources.getColor(R.color.gray_88333333))
                button4.setBackgroundColor(resources.getColor(R.color.gray_88333333))
                var s = StringBuilder()
                trip_days?.forEach {
                    s.append(" " + it.desc)
                }
                text9.text = s
            }
            button4.setOnClickListener{
                button4.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                button3.setBackgroundColor(resources.getColor(R.color.gray_88333333))
                button2.setBackgroundColor(resources.getColor(R.color.gray_88333333))
                text9.text = cancellation_policy
            }

            var food = StringBuilder()
            food_types?.forEach {
                food.append(it.text + ". ")
            }
            servicesList.add(ServisesModel("الطعام والشراب", food.toString()))
            servicesAdapter?.notifyDataSetChanged()

            var funService = StringBuilder()
            entertainment_service_types?.forEach {
                funService.append(it.text + ". ")
            }

            servicesList.add(ServisesModel("المرافق المتوفرة", funService.toString()))
            servicesAdapter?.notifyDataSetChanged()

            trip_comments?.forEach {
                commentsList.add(CommentsModel(it.client.name?: "",
                        it.comment?: "", it.client.image?: "", it.created_at?: "2018-06-19 08:36:55"))
            }
            commentsAdapter?.notifyDataSetChanged()


            button5.setOnClickListener {
                sweetDialogHelper.showCustomWithDialog("أضف تعليق", "تم") {
                    if (TravellawyPrefStore(this@TouristesTripDetailActivity).getPreferenceValue(Constants.AUTHORIZATION, "empty")
                            !!.contentEquals("empty")) {
                        startActivity(Intent(this@TouristesTripDetailActivity, SignInActivity::class.java))
                        overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
                    }else{
                        addComment(sweetDialogHelper, this@TouristesTripDetailActivity,
                                id.toString(), it)
                        commentsList.add(CommentsModel( "loading...", it,  "https://api.adorable.io/avatars/100/abott@adorable.png", "2018-06-19 08:36:55"))
                        commentsAdapter?.notifyDataSetChanged()
                    }
                }
            }

        }

    }

    private fun addComment(sdh: SweetDialogHelper, mContext: Context, tripID: String, comment: String) {
        sdh.showMaterialProgress(getString(R.string.loading))
        val loginNormal = apiService?.commentOnTrip(BuildConfig.Header_Accept,
                TravellawyPrefStore(mContext).getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language, BuildConfig.User_Agent, tripID, comment)
        subscription1 = loginNormal
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Subscriber<TripCommentResponse>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        sdh.dismissDialog()
                        sdh.showErrorMessage("Error!", e.message)
                    }

                    override fun onNext(signUpResult: TripCommentResponse?) {
                        if (signUpResult?.code != 100 || signUpResult?.item == null){
                            sdh.dismissDialog()
                            sdh.showErrorMessage("فشل!", signUpResult?.message)
                            return
                        }

                        sdh.dismissDialog()
                        sdh.showSuccessfulMessage("Done!", "تم التعليق بنجاح") {
                            sdh.dismissDialog()
                        }

                    }
                })

    }


}
