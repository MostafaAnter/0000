package travel.com.signUp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import travel.com.R

import kotlinx.android.synthetic.main.activity_sign_out.*
import kotlinx.android.synthetic.main.content_sign_out.*
import me.iwf.photopicker.PhotoPicker
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import rx.Subscription
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.utility.CircleTransform
import travel.com.utility.Constants
import travel.com.utility.SweetDialogHelper
import travel.com.utility.Util

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
        when(p0.id){
            R.id.addPhoto -> {
                pickPhotoWithPermissionCheck()
            }
            R.id.email_sign_up_button -> {

            }

        }
    }

}
