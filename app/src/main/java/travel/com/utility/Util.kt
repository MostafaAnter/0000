package travel.com.utility

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import android.text.Spannable
import android.text.SpannableString
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.widget.Button


class Util{
    companion object {
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

    }
}