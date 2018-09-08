package travel.com.myBookingFilter

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_my_booking_popup_filter.*
import org.greenrobot.eventbus.EventBus
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util

class MyBookingPopUPFilter : AppCompatActivity(), View.OnClickListener {
    var statusSet: MutableList<String> = mutableListOf()
    var statusObjects: MutableList<StatusObject> = mutableListOf()

    var status: String = "."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_booking_popup_filter)
        changeViewsFonts()
        categoryPicker.setOnClickListener(this)
        button1.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            button1 -> {
                finish()
            }
            categoryPicker -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("أختر حالة")
                        .setSingleChoiceItems(statusSet.toTypedArray(), 0, DialogInterface.OnClickListener { dialog, which ->

                            textCategory.text = statusSet[which]
                            status = statusObjects[which].statusValue
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addDataToDialog()
    }

    override fun onStart() {
        super.onStart()
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun finish() {
        super.finish()
        EventBus.getDefault().post(StatusObject(statusValue = status))
        overridePendingTransition(R.anim.push_up_exit, R.anim.push_up_enter)
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@MyBookingPopUPFilter, Constants.FONT_BOLD, text0, button1)
        Util.changeViewTypeFace(this@MyBookingPopUPFilter, Constants.FONT_REGULAR,
                textCategory)
    }


    fun addDataToDialog(){
        with(statusObjects){
            clear()
            add(StatusObject("فى انتظار السعر", "0"))
            add(StatusObject("فى انتظار تاكيد العميل", "1"))
            add(StatusObject("فى انتظار تاكيد الدفع", "2"))
            add(StatusObject("تم الحجز", "3"))
        }
        with(statusSet){
            clear()
            statusObjects.forEach{
                add(it.statusText)
            }
        }
    }
}
