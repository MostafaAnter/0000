package travel.com.flightSearch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_flight_search.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util
import ru.aviasales.template.ui.fragment.AviasalesFragment
import ru.aviasales.core.AviasalesSDK
import ru.aviasales.core.identification.SdkConfig


class FlightSearchActivity : AppCompatActivity() {

    //Replace these variables on your TravelPayouts marker and token
    private val TRAVEL_PAYOUTS_MARKER = "177987"
    private val TRAVEL_PAYOUTS_TOKEN = "a5ff7999b52d975c5fdd2d4cf0d372fd"
    private val SDK_HOST = "www.travel-api.pw"
    private var aviasalesFragment: AviasalesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialization of AviasalesSDK.
        AviasalesSDK.getInstance().init(this, SdkConfig(TRAVEL_PAYOUTS_MARKER, TRAVEL_PAYOUTS_TOKEN, SDK_HOST))
        setContentView(R.layout.activity_flight_search)
        initFragment()
        changeViewsFonts()
        setToolbar()
    }

    private fun initFragment() {
        val fm = supportFragmentManager

        aviasalesFragment = fm.findFragmentByTag(AviasalesFragment.TAG) as AviasalesFragment? // finding fragment by tag


        if (aviasalesFragment == null) {
            aviasalesFragment = AviasalesFragment.newInstance() as AviasalesFragment?
        }

        val fragmentTransaction = fm.beginTransaction() // adding fragment to fragment manager
        fragmentTransaction.replace(R.id.fragment_place, aviasalesFragment as Fragment, AviasalesFragment.TAG)
        fragmentTransaction.commit()
    }
    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@FlightSearchActivity, Constants.FONT_REGULAR, toolbarTitle)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@FlightSearchActivity, toolbar, R.drawable.ic_arrow_back_wight_24dp, {
            finish()
        }, true)
    }

    override fun onBackPressed() {
        if (!(aviasalesFragment?.onBackPressed()!!)) {
            super.onBackPressed()
        }
    }
}
