package travel.com.touristesPopUpFilter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_popup_filter.*
import travel.com.R
import travel.com.touristesTripResults.TripsResultsActivity
import travel.com.utility.Constants
import travel.com.utility.Util

class PopUPFilter : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0){
            button1 -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_filter)
        changeViewsFonts()
        button1.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_up_exit, R.anim.push_up_enter)
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@PopUPFilter, Constants.FONT_BOLD, text1, text2,
                text3, button1)
        Util.changeViewTypeFace(this@PopUPFilter, Constants.FONT_REGULAR, text4, text5,
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7,
                checkbox8, checkbox9, checkbox10, checkbox11, checkbox12)
    }
}
