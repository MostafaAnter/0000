package travel.com.bookTrip

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_book_trip.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util
import android.widget.RadioButton
import kotlinx.android.synthetic.main.content_book_trip.*
import rx.Subscription
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.touristesCompaniesDetails.CompaniesDetailActivity
import travel.com.touristesTripResults.models.DataItem
import travel.com.utility.SweetDialogHelper


class BookTripActivity : AppCompatActivity() {

    private lateinit var tripItem: DataItem

    private var apiService: ApiInterface? = null
    private var subscription1: Subscription? = null
    private lateinit var sweetDialogHelper: SweetDialogHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_trip)
        changeViewsFonts()
        setToolbar()

        tripItem = intent.getParcelableExtra("item")

        sweetDialogHelper = SweetDialogHelper(this)
        // init api service
        apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        bindView()

    }

    fun changeViewsFonts() {
        Util.changeViewTypeFace(this@BookTripActivity, Constants.FONT_REGULAR, toolbarTitle,
                text1, button1, text2, text3, text4, text7, text6, text8, text9, text10, text11,
                text12, text13, text14, text15, text16, text17, text18, text19, text20,
                text21, text22, text23, text24, text25, text26, text27, text28, text29,
                text30, text31, text32, text33, text34, text, button6, text35, text36,
                text37, text38, text39, radio1, radio2, radio3, radio4, editText1, editText2,
                editText3, editText4, editText5
        )
    }

    fun setToolbar() {
        Util.manipulateToolbar(this@BookTripActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    fun onRadioButtonClicked(view: View) {
        // Is the button now checked?
        val checked = (view as RadioButton).isChecked

        // Check which radio button was clicked
        when (view.getId()) {
            R.id.radio1 -> {
                if (checked){

                }
            }
            R.id.radio2 -> {
                if (checked){

                }
            }
            R.id.radio3 -> {
                if (checked){

                }
            }
            R.id.radio4 -> {
                if (checked){

                }
            }

        }
    }

    fun bindView(){
        with(tripItem){
            text2.text = member?.name
            button1.setOnClickListener {
                startActivity(Intent(this@BookTripActivity, CompaniesDetailActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("item", tripItem.member as Parcelable))
            }

            text3.text = title

            val dayNum = Util.printDifference(Util.conVertDateTextToObject(start_date?: ""),
                    Util.conVertDateTextToObject(end_date?: ""))

            text4.text = "$dayNum" + " أيام" + " / " + "${dayNum - 1}" + " ليالى"
            ratingBar.rating = stars!!.toFloat()

            ratingValue.text =  stars.toFloat().toString()
            text6.text = "from $start_date to $end_date"
        }
    }

}
