package travel.com.utility

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.SpannableString
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


fun AppCompatActivity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

class Util{
    companion object {
        fun getHashKey(mContext: Context) {
            try {
                val info = mContext.packageManager.getPackageInfo(
                        "bugless.apps.wikipets",
                        PackageManager.GET_SIGNATURES)
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                }
            } catch (e: PackageManager.NameNotFoundException) {

            } catch (e: NoSuchAlgorithmException) {

            }

        }
        fun manipulateToolbar(mContext: AppCompatActivity,
                              toolbar: Toolbar,
                              navigationIcon: Int,
                              onClickNavigationAction: () -> Unit,
                              isCustoomTitle: Boolean) {
            mContext.setSupportActionBar(toolbar)

            if (navigationIcon != 0) {
                toolbar.setNavigationIcon(navigationIcon)
                toolbar.setNavigationOnClickListener{
                    onClickNavigationAction()
                }
            }

            if (isCustoomTitle) {
                /*
            * hide title
            * */
                if (mContext.supportActionBar != null)
                    mContext.supportActionBar!!.setDisplayShowTitleEnabled(false)
                //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
                toolbar.setTitle("")
                toolbar.setSubtitle("")
            }
        }

        /**
         * @param mContext current context
         * @param fontPath path to font.ttf ex. "fonts/normal.ttf" if there fonts directory under assets
         * @param views    that views you want to change it type face should extend text view
         */
        fun changeViewTypeFace(mContext: Context, fontPath: String, vararg views: View) {
            val font = Typeface.createFromAsset(mContext.getAssets(), fontPath)
            for (v in views) {
                (v as? TextView)?.typeface = font
                if(v is Button)
                    v.typeface = font
            }

        }

        fun isOnline(mContext: Context): Boolean {
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting

        }

        fun changeFontOfNavigation(mContext: Context, navigationView: NavigationView) {
            val m = navigationView.getMenu()
            for (i in 0 until m.size()) {
                val mi = m.getItem(i)

                //for aapplying a font to subMenu ...
                val subMenu = mi.getSubMenu()
                if (subMenu != null && subMenu!!.size() > 0) {
                    for (j in 0 until subMenu!!.size()) {
                        val subMenuItem = subMenu!!.getItem(j)
                        applyFontToMenuItem(mContext, subMenuItem)
                    }
                }

                //the method we have create in activity
                applyFontToMenuItem(mContext, mi)
            }
        }

        fun applyFontToMenuItem(mContext: Context, mi: MenuItem) {
            val font = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT_BOLD)
            val mNewTitle = SpannableString(mi.getTitle())
            mNewTitle.setSpan(CustomTypefaceSpan("", font), 0, mNewTitle.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            mi.setTitle(mNewTitle)
        }

        //1 minute = 60 seconds
        //1 hour = 60 x 60 = 3600
        //1 day = 3600 x 24 = 86400
        fun printDifference(startDate: Date, endDate: Date) : Long{
            //milliseconds
            var different = endDate.time - startDate.time

            println("startDate : $startDate")
            println("endDate : $endDate")
            println("different : $different")

            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            val elapsedDays = different / daysInMilli
            different %= daysInMilli

            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)

            // return day numbers
            return elapsedDays
        }

        @SuppressLint("SimpleDateFormat")
        fun conVertDateTextToObject(textDate: String) : Date{
            return try {
                val simpleDateFormat = SimpleDateFormat("dd/M/yyyy")
                simpleDateFormat.parse(textDate)
            } catch (e: Exception) {
                Date()
            }
        }

        fun getDate(): String {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd")
            val date = Date()
            return dateFormat.format(date)
        }

    }

    class ReadObjectsFromFile<T>(private val code: (mList: List<T>?) -> Unit, private val file: File) : AsyncTask<Void, Void, List<T>?>() {

        override fun doInBackground(vararg voids: Void): List<T>? {
            try {
                val inStream = FileInputStream(file)
                val objectInStream = ObjectInputStream(inStream)
                val count = objectInStream.readInt() // Get the number of regions
                val items = ArrayList<T>()
                for (c in 0 until count)
                    items.add(objectInStream.readObject() as T)
                objectInStream.close()
                return items
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                return null
            }

        }

        override fun onPostExecute(ts: List<T>?) {
            super.onPostExecute(ts)
            code(ts)
        }
    }

    class saveObjectsInsideFile<T>(private val code: () -> Unit, private val file: File) : AsyncTask<List<T>, Void, Void>() {

        override fun doInBackground(vararg lists: List<T>): Void? {
            try {
                val outStream = FileOutputStream(file)
                val objectOutStream = ObjectOutputStream(outStream)
                objectOutStream.writeInt(lists[0].size) // Save size first
                for (item in lists[0])
                    objectOutStream.writeObject(item)
                objectOutStream.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(v: Void) {
            super.onPostExecute(v)
            code()
        }
    }

}