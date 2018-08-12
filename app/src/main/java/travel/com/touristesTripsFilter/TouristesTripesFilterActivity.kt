package travel.com.touristesTripsFilter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.app_bar_touristes_tripes_filter.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util
import android.widget.RadioButton
import android.widget.TextView
import kotlinx.android.synthetic.main.content_touristes_tripes_filter.*
import travel.com.touristesTripResults.TripsResultsActivity
import travel.com.utility.DatePickerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import travel.com.utility.MessageEvent
import android.content.DialogInterface
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AlertDialog
import travel.com.loaders.GetCitiesAsyncTaskLoader
import travel.com.loaders.GetCitiesOfEgyAsyncTaskLoader
import travel.com.loaders.GetCountriesAsyncTaskLoader
import java.util.*


class TouristesTripesFilterActivity : AppCompatActivity(), View.OnClickListener {
    var cities: MutableList<String> = mutableListOf()
    var citiesOfEgy: MutableList<String> = mutableListOf()
    var countries: MutableList<String> = mutableListOf()
    var countriesObjects: MutableList<CountryItem> = mutableListOf()
    var citiesObjects: MutableList<CityItem> = mutableListOf()
    var citiesObjectsOfEgy: MutableList<CityItem> = mutableListOf()
    var  countryId: Int = 0
    var IsEgy: Boolean = true


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.button1 -> {
                startActivity(Intent(this@TouristesTripesFilterActivity, TripsResultsActivity::class.java))
            }
            R.id.date_picker ->{
                val newFragment = DatePickerFragment()
                newFragment.show(supportFragmentManager, "datePicker")
            }
            R.id.city_picker -> {
                val builder = AlertDialog.Builder(this)
                if (!IsEgy) {
                    builder.setTitle("اختر المدينة")
                            .setSingleChoiceItems(cities.toTypedArray(), 0, DialogInterface.OnClickListener { dialog, which ->
                                // The 'which' argument contains the index position
                                // of the selected item
                                text2.text = cities.get(which)
                            })
                    builder.setPositiveButton("Okey", { dialog, which ->
                        dialog.dismiss()
                     })
                    builder.create().show()
                } else {
                    builder.setTitle("اختر المدينة")
                            .setSingleChoiceItems(citiesOfEgy.toTypedArray(), 0, DialogInterface.OnClickListener { dialog, which ->
                                // The 'which' argument contains the index position
                                // of the selected item
                                text2.text = citiesOfEgy.get(which)
                            })
                    builder.setPositiveButton("Okey", { dialog, which ->
                        dialog.dismiss()
                    })
                    builder.create().show()
                }
            }

            R.id.country_picker -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("اختر الدولة")
                        .setSingleChoiceItems(countries.toTypedArray(), 0, DialogInterface.OnClickListener { dialog, which ->
                            // The 'which' argument contains the index position
                            // of the selected item
                            text00.text = countries[which]
                            countryId = countriesObjects[which].id
                            supportLoaderManager.initLoader(1, null, getCitiesLoader)
                        })
                builder.setPositiveButton("Okey", { dialog, which ->
                    dialog.dismiss()
                })
                builder.create().show()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_touristes_tripes_filter)

        setToolbar()
        changeViewsFonts()

        button1.setOnClickListener(this)
        country_picker.setOnClickListener(this)
        date_picker.setOnClickListener(this)
        city_picker.setOnClickListener(this)

        onRadioButtonClicked(radio1)
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {

     text4.text = "${event.year}/${event.month}/${event.day}"
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@TouristesTripesFilterActivity, Constants.FONT_REGULAR, toolbarTitle,
                radio1, radio2, text0, text00, text1, text2, text3, text4, button1)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@TouristesTripesFilterActivity, toolbar,
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
                    // Pirates are the best
                    selectCountryView.visibility = View.GONE
                    IsEgy = true
                    supportLoaderManager.initLoader(2, null, getCitiesOfEgyLoader)
                }
                else{
                    selectCountryView.visibility = View.VISIBLE
                    supportLoaderManager.initLoader(0, null, getCountriesLoader)
                }
            }
            R.id.radio2 -> {
                if (checked) {
                    // Pirates are the best
                    selectCountryView.visibility = View.VISIBLE
                    supportLoaderManager.initLoader(0, null, getCountriesLoader)
                    IsEgy = false
                }
                else{
                    selectCountryView.visibility = View.GONE
                }
            }
        }
    }

    // get countries
    private val getCountriesLoader = object : LoaderManager.LoaderCallbacks<List<CountryItem>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<CountryItem>> {
            return GetCountriesAsyncTaskLoader(this@TouristesTripesFilterActivity)
        }

        override fun onLoadFinished(
                loader: Loader<List<CountryItem>>, data: List<CountryItem>?) {
            // Display our data, for instance updating our adapter
            if (data != null) {
                with(countriesObjects){
                    clear()
                    data.forEach{
                        add(it)
                    }
                }
                with(countries){
                    clear()
                    data.forEach{
                        add(it.text)
                    }
                }
                text00.text = countries[0]
            }
        }

        override fun onLoaderReset(loader: Loader<List<CountryItem>>) {
            // Loader reset, throw away our data,
            // unregister any listeners, etc.
            // Of course, unless you use destroyLoader(),
            // this is called when everything is already dying
            // so a completely empty onLoaderReset() is
            // totally acceptable
        }
    }

    // get cities
    private val getCitiesLoader = object : LoaderManager.LoaderCallbacks<List<CityItem>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<CityItem>> {
            return GetCitiesAsyncTaskLoader(this@TouristesTripesFilterActivity, countryId)
        }

        override fun onLoadFinished(
                loader: Loader<List<CityItem>>, data: List<CityItem>?) {
            // Display our data, for instance updating our adapter
            if (data != null) {
                with(citiesObjects){
                    clear()
                    data.forEach{
                        add(it)
                    }
                }
                with(cities){
                    clear()
                    data.forEach{
                        add(it.text)
                    }
                }
                text2.text = cities[0]
            }
        }

        override fun onLoaderReset(loader: Loader<List<CityItem>>) {
            // Loader reset, throw away our data,
            // unregister any listeners, etc.
            // Of course, unless you use destroyLoader(),
            // this is called when everything is already dying
            // so a completely empty onLoaderReset() is
            // totally acceptable
        }
    }

    // get citiesOfEgy
    private val getCitiesOfEgyLoader = object : LoaderManager.LoaderCallbacks<List<CityItem>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<CityItem>> {
            return GetCitiesOfEgyAsyncTaskLoader(this@TouristesTripesFilterActivity)
        }

        override fun onLoadFinished(
                loader: Loader<List<CityItem>>, data: List<CityItem>?) {
            // Display our data, for instance updating our adapter
            if (data != null) {
                with(citiesObjectsOfEgy){
                    clear()
                    data.forEach{
                        add(it)
                    }
                }
                with(citiesOfEgy){
                    clear()
                    data.forEach{
                        add(it.text)
                    }
                }
                text2.text = cities[0]
            }
        }

        override fun onLoaderReset(loader: Loader<List<CityItem>>) {
            // Loader reset, throw away our data,
            // unregister any listeners, etc.
            // Of course, unless you use destroyLoader(),
            // this is called when everything is already dying
            // so a completely empty onLoaderReset() is
            // totally acceptable
        }
    }

}
