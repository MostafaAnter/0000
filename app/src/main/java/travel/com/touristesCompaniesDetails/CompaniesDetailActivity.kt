package travel.com.touristesCompaniesDetails

import android.Manifest
import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_companies_detail.*
import kotlinx.android.synthetic.main.content_companies_detail.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.R
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.signIn.SignInActivity
import travel.com.store.TravellawyPrefStore
import travel.com.touristesCompanies.models.CommentResponse
import travel.com.touristesCompanies.models.DataItem
import travel.com.utility.Constants
import travel.com.utility.SweetDialogHelper
import travel.com.utility.Util
import java.util.ArrayList

@RuntimePermissions
class CompaniesDetailActivity : AppCompatActivity() {

    // for comments
    private var commentsAdapter: CommentsAdapter? = null
    private val commentsList = ArrayList<CommentsModel>()

    private var apiService: ApiInterface? = null
    private var subscription1: Subscription? = null


    private lateinit var tripItem: DataItem

    private lateinit var sweetDialogHelper: SweetDialogHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companies_detail)
        setToolbar()
        changeViewsFonts()
        setCommentsAdapter()

        tripItem = intent.getParcelableExtra("item")

        sweetDialogHelper = SweetDialogHelper(this)
        // init api service
        apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        bindData()

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

            }
        })


    }

    @NeedsPermission(CALL_PHONE)
    internal fun makeCall(phoneNum: String?) {
        val intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse("tel:$phoneNum")
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun bindData() {
        with(tripItem) {
            Glide.with(this@CompaniesDetailActivity)   // pass Context
                    .load(image) // add your image url
                    .into(image1)

            text1.text = about

            ratingBar.rating = stars!!.toFloat()
            ratingValue.text =  stars.toFloat().toString()

            button1.setOnClickListener {
                sweetDialogHelper.showCustomWithDialog("أضف تعليق", "تم") {
                    if (TravellawyPrefStore(this@CompaniesDetailActivity).getPreferenceValue(Constants.AUTHORIZATION, "empty")
                            !!.contentEquals("empty")) {
                        startActivity(Intent(this@CompaniesDetailActivity, SignInActivity::class.java))
                        overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
                    }else{
                        addComment(sweetDialogHelper, this@CompaniesDetailActivity,
                                id.toString(), it)
                        commentsList.add(CommentsModel( "loading...", it,  "https://api.adorable.io/avatars/100/abott@adorable.png", "2018-06-19 08:36:55"))
                        commentsAdapter?.notifyDataSetChanged()
                    }
                }
            }
            button2.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO) // it's not ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Travelawy feedback")
                intent.putExtra(Intent.EXTRA_TEXT, "")
                intent.data = Uri.parse("mailto:$email") // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent)
            }
            button3.setOnClickListener {
                makeCallWithPermissionCheck(mobile)
            }

            member_comments?.forEach {
                commentsList.add(CommentsModel(it.client.name?: "",
                        it.comment?: "", it.client.image?: "", it.created_at?: "2018-06-19 08:36:55"))
            }
            commentsAdapter?.notifyDataSetChanged()
        }
    }

    private fun addComment(sdh: SweetDialogHelper, mContext: Context, companyID: String, comment: String) {
        sdh.showMaterialProgress(getString(R.string.loading))
        val loginNormal = apiService?.commentOnCompany(BuildConfig.Header_Accept,
                TravellawyPrefStore(mContext).getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language, BuildConfig.User_Agent, companyID, comment)
        subscription1 = loginNormal
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Subscriber<CommentResponse>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        sdh.dismissDialog()
                        sdh.showErrorMessage("Error!", e.message)
                    }

                    override fun onNext(signUpResult: CommentResponse?) {
                        if (signUpResult?.code != 100 || signUpResult?.item == null){
                            sdh.dismissDialog()
                            sdh.showErrorMessage("فشل!", signUpResult?.message)
                            return
                        }

                        sdh.dismissDialog()
                        sdh.showSuccessfulMessage("Done!", "تم التعليق بنجاح") {
                            finish()
                        }

                    }
                })

    }


}
