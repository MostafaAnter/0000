package travel.com.signUp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import travel.com.R

import kotlinx.android.synthetic.main.activity_sign_out.*
import kotlinx.android.synthetic.main.content_sign_out.*
import me.iwf.photopicker.PhotoPicker
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.signIn.SignInResponse
import travel.com.store.TravellawyPrefStore
import travel.com.utility.CircleTransform
import travel.com.utility.Constants
import travel.com.utility.SweetDialogHelper
import travel.com.utility.Util
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

@RuntimePermissions
class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "SignUpActivity"

    private lateinit var sdh: SweetDialogHelper

    private var apiService: ApiInterface? = null
    private var subscription1: Subscription? = null
    private var theBitmap: Bitmap? = null
    private var profileImagePath: String? = null
    private var user_email: String? = null


    private var user_password: String? = null
    private var user_full_name: String? = null
    private var user_mobile: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_out)
        changeViewsFonts()
        setToolbar()

        // init dialog
        sdh = SweetDialogHelper(this)

        // init api service
        apiService = ApiClient.getClient().create(ApiInterface::class.java)

        // init user photo
        Glide.with(this@SignUpActivity)   // pass Context
                .load(R.drawable.login_title) // add your image url
                .transform(CircleTransform(this@SignUpActivity)) // applying the image transformer
                .into(top_logo)

        addPhoto.setOnClickListener(this)
        email_sign_up_button.setOnClickListener(this)
    }

    fun changeViewsFonts() {
        Util.changeViewTypeFace(this@SignUpActivity, Constants.FONT_REGULAR, toolbarTitle,
                addPhoto, text1, text2, text3, text4, email_sign_up_button)
    }

    fun setToolbar() {
        Util.manipulateToolbar(this@SignUpActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    @NeedsPermission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun pickPhoto() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                val photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)

                profileImagePath = photos[0]

                Glide.with(this@SignUpActivity)   // pass Context
                        .load(photos[0]) // add your image url
                        .transform(CircleTransform(this@SignUpActivity)) // applying the image transformer
                        .into(top_logo)
                top_logo.setPadding(10, 10, 10, 10)

                // sign image to the bitmap
                Glide.with(this@SignUpActivity)
                        .load(photos[0])
                        .asBitmap()
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                                theBitmap = resource
                            }
                        })
            }
        }
    }


    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.addPhoto -> {
                pickPhotoWithPermissionCheck()
            }
            R.id.email_sign_up_button -> {

                if (Util.isOnline(this)) {
                    if (checkLoginValidation()) {
                        registerNormal(sdh, this)
                    }
                } else {
                    sdh?.showErrorMessage("فشل !", "قم بفحص أتصال الأنترنت")
                }


            }

        }
    }

    private fun checkLoginValidation(): Boolean {
        user_full_name = fullName.text.toString().trim{ it <= ' ' }
        user_mobile = phone.text.toString().trim{ it <= ' ' }
        user_email = email.text.toString().trim { it <= ' ' }
        user_password = password.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(user_email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            sdh?.showErrorMessage("فشل !", "أدخل بريد الكترونى صالح.")
            return false
        }

        if (TextUtils.isEmpty(user_password) || user_password!!.length <= 4) {
            sdh?.showErrorMessage("فشل !", "رقم سري ضعيف")
            return false
        }
        if (TextUtils.isEmpty(user_full_name)) {
            sdh?.showErrorMessage("فشل !", "ادخل الاسم")
            return false
        }
        if (TextUtils.isEmpty(user_mobile)) {
            sdh?.showErrorMessage("فشل !", "ادخل الهاتف")
            return false
        }

        return true
    }

//    @Throws(IOException::class)
//    fun getBytes(inputStream: InputStream?): ByteArray {
//        val byteBuff = ByteArrayOutputStream()
//
//        val buffSize = 1024
//        val buff = ByteArray(buffSize)
//
//        var len = 0
//        while ({len = inputStream?.read(buff)!!; len}() != -1) {
//            byteBuff.write(buff, 0, len)
//        }
//
//        return byteBuff.toByteArray()
//    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray {
        val byteBuff = ByteArrayOutputStream()

        val buffSize = 1024
        val buff = ByteArray(buffSize)

        var len = 0
        while ({len = inputStream.read(buff); len}() != -1) {
            byteBuff.write(buff, 0, len)
        }

        return byteBuff.toByteArray()
    }

    fun registerNormal(sdh: SweetDialogHelper, mContext: Context) {
        sdh.showMaterialProgress(getString(R.string.loading))


        // image as file
        val file = File(profileImagePath)
        val inputStream = contentResolver.openInputStream(Uri.fromFile(file))
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(inputStream))
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        Log.d("nama file e cuk", file.name)

        val signup = apiService?.signUp(BuildConfig.Header_Accept,
                TravellawyPrefStore(mContext).getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language, BuildConfig.User_Agent,
                RequestBody.create(MediaType.parse("text/plain"), user_full_name),
                RequestBody.create(MediaType.parse("text/plain"), user_email),
                RequestBody.create(MediaType.parse("text/plain"), user_mobile),
                RequestBody.create(MediaType.parse("text/plain"), user_password),
                RequestBody.create(MediaType.parse("text/plain"), user_password),
                body)

        if (signup != null) {
            subscription1 = signup
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<SignInResponse>() {
                        override fun onNext(t: SignInResponse?) {
                            if (t?.code == 100) {// save user data
                                t?.item?.authorization?.let { TravellawyPrefStore(mContext).addPreference(Constants.AUTHORIZATION, it) }
                                t?.item?.id?.let { TravellawyPrefStore(mContext).addPreference(Constants.USER_ID, it) }
                                sdh.dismissDialog()
                                sdh.showSuccessfulMessage("Welcome!", t?.item?.name) {
                                    finish()
                                }
                            } else {
                                sdh.dismissDialog()
                                sdh?.showErrorMessage("فشل !", t?.message)
                            }
                        }

                        override fun onCompleted() {
                            Log.d("complete", "complete")
                        }

                        override fun onError(e: Throwable?) {
                            Log.e("error", e?.message)
                        }
                    })
        }
    }


}
