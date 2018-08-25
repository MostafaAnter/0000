package travel.com.signIn

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import travel.com.R

import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.content_sign_in.*
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.signUp.SignUpActivity
import travel.com.store.TravellawyPrefStore
import travel.com.utility.Constants
import travel.com.utility.SweetDialogHelper
import travel.com.utility.Util
import java.lang.Exception

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var sdh :SweetDialogHelper
    private var apiService: ApiInterface? = null
    private var subscription1: Subscription? = null


    // handel user data
    private var theBitmap: Bitmap? = null
    private var user_email: String? = null
    private var user_passsword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        changeViewsFonts()
        setToolbar()

        // init dialog
        sdh = SweetDialogHelper(this)

        // init api service
        apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)

        sign_up_button.setOnClickListener(this)
        email_sign_in_button.setOnClickListener(this)

    }

    fun changeViewsFonts() {
        Util.changeViewTypeFace(this@SignInActivity, Constants.FONT_REGULAR, toolbarTitle,
                email, password, sign_up_button, email_sign_in_button, text1, text2)
    }

    override fun onStart() {
        super.onStart()
        if (!TravellawyPrefStore(this).getPreferenceValue(Constants.AUTHORIZATION, "empty")
                        !!.contentEquals("empty")) {
            finish()
        }
    }

    fun setToolbar() {
        Util.manipulateToolbar(this@SignInActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.sign_up_button -> {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
            R.id.email_sign_in_button -> {
                if (Util.isOnline(this)) {
                    if (checkLoginValidation()) {
                        loginNormal(sdh, this)
                    }
                } else {
                    sdh?.showErrorMessage("فشل !", "قم بفحص أتصال الأنترنت")
                }
            }
        }
    }

    private fun checkLoginValidation(): Boolean {
        user_email = email.text.toString().trim { it <= ' ' }
        user_passsword = password.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(user_email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            sdh?.showErrorMessage("فشل !", "أدخل بريد الكترونى صالح.")
            return false
        }

        if (TextUtils.isEmpty(user_passsword) || user_passsword!!.length <= 4) {
            sdh?.showErrorMessage("فشل !", "رقم سري ضعيف")
            return false
        }

        return true
    }

    private fun loginNormal(sdh: SweetDialogHelper, mContext: Context) {
        sdh.showMaterialProgress(getString(R.string.loading))
        val loginNormal = apiService?.login(BuildConfig.Header_Accept,
                TravellawyPrefStore(mContext).getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language, BuildConfig.User_Agent, user_email, user_passsword)
        subscription1 = loginNormal
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Subscriber<SignInResponse>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        sdh.dismissDialog()
                        sdh.showErrorMessage("Error!", e.message)
                    }

                    override fun onNext(signUpResult: SignInResponse?) {
                        if (signUpResult?.code != 100 || signUpResult?.item == null){
                            sdh.dismissDialog()
                            sdh.showErrorMessage("فشل!", signUpResult?.message)
                            return
                        }
                        // save user data
                        signUpResult?.item?.authorization?.let { TravellawyPrefStore(mContext).addPreference(Constants.AUTHORIZATION, it) }
                        signUpResult?.item?.id?.let { TravellawyPrefStore(mContext).addPreference(Constants.USER_ID, it) }

                        sdh.dismissDialog()
                        sdh.showSuccessfulMessage("Welcome!", signUpResult?.item?.name) {
                            finish()
                        }

                    }
                })

    }

}
