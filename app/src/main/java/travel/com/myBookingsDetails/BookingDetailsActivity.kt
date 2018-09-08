package travel.com.myBookingsDetails

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import travel.com.R

import kotlinx.android.synthetic.main.activity_booking_details.*
import kotlinx.android.synthetic.main.content_booking_details.*
import travel.com.myBookings.models.DataItem
import travel.com.utility.CircleTransform
import travel.com.utility.Constants
import travel.com.utility.Util

class BookingDetailsActivity : AppCompatActivity() {

    private lateinit var tripItem: DataItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
        setToolbar()
        changeViewsFonts()

        tripItem = intent.getParcelableExtra("item")

        bindData()
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@BookingDetailsActivity, Constants.FONT_REGULAR, toolbarTitle,
                item_txt_message, item_txt_title, button4, text6, text11, text12)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@BookingDetailsActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    fun bindData(){
        with(tripItem){
            Glide.with(this@BookingDetailsActivity)   // pass Context
                    .load(trip.image)
                    .transform(CircleTransform(this@BookingDetailsActivity))// add your image url
                    .into(img_user)


            item_txt_title.text = this?.trip?.title

            // todo difference between start date and end date
            val dayNum = Util.printDifference(Util.conVertDateTextToObject(this?.trip?.start_date?: ""),
                    Util.conVertDateTextToObject(this?.trip?.end_date?: ""))
            item_txt_message.text = "$dayNum" + " أيام" + " / " + "${dayNum - 1}" + " ليالى"

            ratingBar.rating = this?.trip?.stars!!.toFloat()
            ratingValue.text = this.trip.stars.toFloat().toString()

            text6.text = "from ${trip.start_date} to ${trip.end_date}"
            var s = StringBuilder()
            s.append( "عدد الأشخاص " + "${adult_count}\n" +
                    "عدد الأطفال " + "${child_count}\n")
            child_ages?.forEachIndexed{ index, childAges ->
                s.append("سن الطفل " + "${index + 1}")
                s.append(" " + "${childAges.value}\n")
            }

            s.append( "عدد الغرف " + "${room_count}\n")
            room_types?.forEachIndexed { index, roomTypes ->
                s.append("نوع الغرفة " + "${index + 1}")
                s.append(" " + "${roomTypes.value}\n")
            }

            s.append("\n$payment_method_text")

            text12.text = s.toString()

            if (price.isNullOrBlank()){
                button4.text = status_txt
            }else{
                button4.text = "الأجمالى " + price
            }



        }
    }
}
