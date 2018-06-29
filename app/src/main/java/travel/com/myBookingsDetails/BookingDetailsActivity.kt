package travel.com.myBookingsDetails

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import travel.com.R

import kotlinx.android.synthetic.main.activity_booking_details.*
import kotlinx.android.synthetic.main.content_booking_details.*
import travel.com.utility.CircleTransform
import travel.com.utility.Constants
import travel.com.utility.Util

class BookingDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
        setToolbar()
        changeViewsFonts()
        Glide.with(this)   // pass Context
                .load(R.drawable.cdn)
                .transform(CircleTransform(this))// add your image url
                .into(img_user)
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@BookingDetailsActivity, Constants.FONT_REGULAR, toolbarTitle,
                item_txt_message, item_txt_title, button4)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@BookingDetailsActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }
}
