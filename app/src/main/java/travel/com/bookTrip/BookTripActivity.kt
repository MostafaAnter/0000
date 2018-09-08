package travel.com.bookTrip

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_book_trip.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util
import android.widget.RadioButton
import kotlinx.android.synthetic.main.content_book_trip.*
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import travel.com.BuildConfig
import travel.com.bookTrip.models.ReservationResponse
import travel.com.rest.ApiClient
import travel.com.rest.ApiInterface
import travel.com.store.TravellawyPrefStore
import travel.com.touristesCompaniesDetails.CompaniesDetailActivity
import travel.com.touristesTripResults.models.DataItem
import travel.com.utility.SweetDialogHelper


class BookTripActivity : AppCompatActivity(), View.OnClickListener {

    var bigNumbersList: MutableList<String> = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    var smallNumbersList: MutableList<String> = mutableListOf("1", "2", "3", "4", "5")
    var childrenAges: MutableList<String> = mutableListOf("1", "2")
    var childrenAgesText: MutableList<String> = mutableListOf("من 0 الى 5 سنوات", "من 6 الى 11 سنة")
    var roomsTypes: MutableList<String> = mutableListOf("1", "2", "3")
    var roomsTypesText: MutableList<String> = mutableListOf("فردية", "مزدوجة", "ثلاثية")



    private lateinit var tripItem: DataItem

    private var apiService: ApiInterface? = null
    private var subscription1: Subscription? = null
    private lateinit var sweetDialogHelper: SweetDialogHelper

    // for make reservation request
    var trip_id: String? = null
    var adult_count: String? = "0"
    var child_count: String? = "0"
    var room_count: String? = "0"
    var room_types = ArrayList<String>()
    var child_ages = ArrayList<String>()
    var payment_method: String? = null
    var note: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_trip)
        changeViewsFonts()
        setToolbar()

        tripItem = intent.getParcelableExtra("item")
        trip_id = tripItem.id.toString()
        sweetDialogHelper = SweetDialogHelper(this)
        // init api service
        apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        bindView()

        adults_num_picker.setOnClickListener(this)
        child_count_picker.setOnClickListener(this)
        room_count_picker.setOnClickListener(this)
        child1_age.setOnClickListener(this)
        child2_age.setOnClickListener(this)
        child3_age.setOnClickListener(this)
        child4_age.setOnClickListener(this)
        child5_age.setOnClickListener(this)
        room1_type.setOnClickListener(this)
        room2_type.setOnClickListener(this)
        room3_type.setOnClickListener(this)
        room4_type.setOnClickListener(this)
        room5_type.setOnClickListener(this)
        button6.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0){
            adults_num_picker-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("عدد الاشخاص البالغين")
                        .setSingleChoiceItems(bigNumbersList.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text9.text = bigNumbersList[which]
                            adult_count = bigNumbersList[which]
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            child_count_picker-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("عدد الأطفال")
                        .setSingleChoiceItems(smallNumbersList.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text11.text = smallNumbersList[which]
                            child_count = smallNumbersList[which]

                            when(child_count!!.toInt()){
                                1 ->{
                                    child_ages.clear()
                                    text13.text = "سن الطفل 1"
                                    child1_age.visibility = View.VISIBLE
                                    child2_age.visibility = View.GONE
                                    child3_age.visibility = View.GONE
                                    child4_age.visibility = View.GONE
                                    child5_age.visibility = View.GONE
                                }
                                2 ->{
                                    child_ages.clear()
                                    text13.text = "سن الطفل 1"
                                    text15.text = "سن الطفل 2"
                                    child1_age.visibility = View.VISIBLE
                                    child2_age.visibility = View.VISIBLE
                                    child3_age.visibility = View.GONE
                                    child4_age.visibility = View.GONE
                                    child5_age.visibility = View.GONE
                                }
                                3 ->{
                                    child_ages.clear()
                                    text13.text = "سن الطفل 1"
                                    text15.text = "سن الطفل 2"
                                    text17.text = "سن الطفل 3"
                                    child1_age.visibility = View.VISIBLE
                                    child2_age.visibility = View.VISIBLE
                                    child3_age.visibility = View.VISIBLE
                                    child4_age.visibility = View.GONE
                                    child5_age.visibility = View.GONE
                                }
                                4 ->{
                                    child_ages.clear()
                                    text13.text = "سن الطفل 1"
                                    text15.text = "سن الطفل 2"
                                    text17.text = "سن الطفل 3"
                                    text19.text = "سن الطفل 4"
                                    child1_age.visibility = View.VISIBLE
                                    child2_age.visibility = View.VISIBLE
                                    child3_age.visibility = View.VISIBLE
                                    child4_age.visibility = View.VISIBLE
                                    child5_age.visibility = View.GONE
                                }
                                5 ->{
                                    child_ages.clear()
                                    text13.text = "سن الطفل 1"
                                    text15.text = "سن الطفل 2"
                                    text17.text = "سن الطفل 3"
                                    text19.text = "سن الطفل 4"
                                    text21.text = "سن الطفل 5"
                                    child1_age.visibility = View.VISIBLE
                                    child2_age.visibility = View.VISIBLE
                                    child3_age.visibility = View.VISIBLE
                                    child4_age.visibility = View.VISIBLE
                                    child5_age.visibility = View.VISIBLE
                                }
                                else ->{

                                }
                            }


                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            room_count_picker-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("عدد الغرف")
                        .setSingleChoiceItems(smallNumbersList.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text23.text = smallNumbersList[which]
                            room_count = smallNumbersList[which]

                            when(room_count!!.toInt()){
                                1 ->{
                                    room_types.clear()
                                    text25.text = "نوع الغرفة"
                                    room1_type.visibility = View.VISIBLE
                                    room2_type.visibility = View.GONE
                                    room3_type.visibility = View.GONE
                                    room4_type.visibility = View.GONE
                                    room5_type.visibility = View.GONE
                                }
                                2 ->{
                                    room_types.clear()
                                    text25.text = "نوع الغرفة"
                                    text27.text = "نوع الغرفة"
                                    room1_type.visibility = View.VISIBLE
                                    room2_type.visibility = View.VISIBLE
                                    room3_type.visibility = View.GONE
                                    room4_type.visibility = View.GONE
                                    room5_type.visibility = View.GONE
                                }
                                3 ->{
                                    room_types.clear()
                                    text25.text = "نوع الغرفة"
                                    text27.text = "نوع الغرفة"
                                    text29.text = "نوع الغرفة"
                                    room1_type.visibility = View.VISIBLE
                                    room2_type.visibility = View.VISIBLE
                                    room3_type.visibility = View.VISIBLE
                                    room4_type.visibility = View.GONE
                                    room5_type.visibility = View.GONE
                                }
                                4 ->{
                                    room_types.clear()
                                    text25.text = "نوع الغرفة"
                                    text27.text = "نوع الغرفة"
                                    text29.text = "نوع الغرفة"
                                    text31.text = "نوع الغرفة"
                                    room1_type.visibility = View.VISIBLE
                                    room2_type.visibility = View.VISIBLE
                                    room3_type.visibility = View.VISIBLE
                                    room4_type.visibility = View.VISIBLE
                                    room5_type.visibility = View.GONE
                                }
                                5 ->{
                                    room_types.clear()
                                    text25.text = "نوع الغرفة"
                                    text27.text = "نوع الغرفة"
                                    text29.text = "نوع الغرفة"
                                    text31.text = "نوع الغرفة"
                                    text33.text = "نوع الغرفة"
                                    room1_type.visibility = View.VISIBLE
                                    room2_type.visibility = View.VISIBLE
                                    room3_type.visibility = View.VISIBLE
                                    room4_type.visibility = View.VISIBLE
                                    room5_type.visibility = View.VISIBLE
                                }
                                else ->{

                                }
                            }


                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }

            child1_age-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("سن الطفل")
                        .setSingleChoiceItems(childrenAgesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text13.text = childrenAgesText[which]
                            child_ages.add(childrenAges[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            child2_age-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("سن الطفل")
                        .setSingleChoiceItems(childrenAgesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text15.text = childrenAgesText[which]
                            child_ages.add(childrenAges[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            child3_age-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("سن الطفل")
                        .setSingleChoiceItems(childrenAgesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text17.text = childrenAgesText[which]
                            child_ages.add(childrenAges[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            child4_age-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("سن الطفل")
                        .setSingleChoiceItems(childrenAgesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text19.text = childrenAgesText[which]
                            child_ages.add(childrenAges[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            child5_age-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("سن الطفل")
                        .setSingleChoiceItems(childrenAgesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text21.text = childrenAgesText[which]
                            child_ages.add(childrenAges[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }

            room1_type-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("نوع الغرفة")
                        .setSingleChoiceItems(roomsTypesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text25.text = roomsTypesText[which]
                            room_types.add(roomsTypes[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            room2_type-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("نوع الغرفة")
                        .setSingleChoiceItems(roomsTypesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text27.text = roomsTypesText[which]
                            room_types.add(roomsTypes[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            room3_type-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("نوع الغرفة")
                        .setSingleChoiceItems(roomsTypesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text29.text = roomsTypesText[which]
                            room_types.add(roomsTypes[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            room4_type-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("نوع الغرفة")
                        .setSingleChoiceItems(roomsTypesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text31.text = roomsTypesText[which]
                            room_types.add(roomsTypes[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            room5_type-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("نوع الغرفة")
                        .setSingleChoiceItems(roomsTypesText.toTypedArray(), -1, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            text33.text = roomsTypesText[which]
                            room_types.add(roomsTypes[which])
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            button6 ->{
                if (Util.isOnline(this)) {
                    if (checkReservationValidation()) {
                        reserveTrip(sweetDialogHelper, this)
                    }
                } else {
                    sweetDialogHelper.showErrorMessage("فشل !", "قم بفحص أتصال الأنترنت")
                }
            }
        }
    }

    fun changeViewsFonts() {
        Util.changeViewTypeFace(this@BookTripActivity, Constants.FONT_REGULAR, toolbarTitle,
                text1, button1, text2, text3, text4, text7, text6, text8, text9, text10, text11,
                text12, text13, text14, text15, text16, text17, text18, text19, text20,
                text21, text22, text23, text24, text25, text26, text27, text28, text29,
                text30, text31, text32, text33, text34, text, button6, text39, radio1, radio2, radio3, radio4, editText5
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
                    payment_method = "4"
                }
            }
            R.id.radio2 -> {
                if (checked){
                    payment_method = "3"
                }
            }
            R.id.radio3 -> {
                if (checked){
                    payment_method = "2"
                }
            }
            R.id.radio4 -> {
                if (checked){
                    payment_method = "1"
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

            // hide child age and room types
            child1_age.visibility = View.GONE
            child2_age.visibility = View.GONE
            child3_age.visibility = View.GONE
            child4_age.visibility = View.GONE
            child5_age.visibility = View.GONE

            room1_type.visibility = View.GONE
            room2_type.visibility = View.GONE
            room3_type.visibility = View.GONE
            room4_type.visibility = View.GONE
            room5_type.visibility = View.GONE
        }
    }

    private fun checkReservationValidation(): Boolean {
        note = editText5.text.toString().trim{ it <= ' ' }
        if (adult_count?.toInt()!! < 1) {
            sweetDialogHelper?.showErrorMessage("فشل !", "حدد عدد الأشخاص")
            return false
        }
        if (child_count?.toInt()!! > childrenAges.size) {
            sweetDialogHelper?.showErrorMessage("فشل !", "حدد سن الأطفال")
            return false
        }
        if (room_count?.toInt()!! > room_types.size) {
            sweetDialogHelper?.showErrorMessage("فشل !", "حدد نوع الغرف")
            return false
        }
        if (room_count?.toInt()!! < 1) {
            sweetDialogHelper?.showErrorMessage("فشل !", "حدد عدد الغرف")
            return false
        }
        if (payment_method == null) {
            sweetDialogHelper?.showErrorMessage("فشل !", "أختر طريقة دفع")
            return false
        }
        if (TextUtils.isEmpty(note)) {
            sweetDialogHelper?.showErrorMessage("فشل !", "أدخل الملاحضات")
            return false
        }

        return true
    }

    private fun reserveTrip(sdh: SweetDialogHelper, mContext: Context) {
        sdh.showMaterialProgress(getString(R.string.loading))
        val loginNormal = apiService?.reserveTrip(BuildConfig.Header_Accept,
                TravellawyPrefStore(mContext).getPreferenceValue(Constants.AUTHORIZATION, "empty"),
                BuildConfig.From,
                BuildConfig.Accept_Language, BuildConfig.User_Agent,
                trip_id, adult_count, child_count, room_count, room_types, child_ages, payment_method, note)
        subscription1 = loginNormal
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Subscriber<ReservationResponse>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        sdh.dismissDialog()
                        sdh.showErrorMessage("Error!", e.message)
                    }

                    override fun onNext(signUpResult: ReservationResponse?) {
                        if (signUpResult?.code != 100 ){
                            sdh.dismissDialog()
                            sdh.showErrorMessage("فشل!", "طريقة دفع غير متاحة الان")
                            return
                        }

                        sdh.dismissDialog()
                        sdh.showSuccessfulMessage("Done!", "تم ارسال طلب الحجز بنجاح.") {
                            finish()
                        }

                    }
                })

    }


}
