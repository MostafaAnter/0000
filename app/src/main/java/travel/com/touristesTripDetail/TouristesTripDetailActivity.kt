package travel.com.touristesTripDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_touristes_trip_detail.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util

class TouristesTripDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touristes_trip_detail)

        changeViewsFonts()
        setToolbar()
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@TouristesTripDetailActivity, Constants.FONT_REGULAR, toolbarTitle)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@TouristesTripDetailActivity, toolbar,
                R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

}
