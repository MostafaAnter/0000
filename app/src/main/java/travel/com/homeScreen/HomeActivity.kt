
package travel.com.homeScreen

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import travel.com.R
import travel.com.utility.Constants
import travel.com.utility.Util

class HomeActivity : LocalizationActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onClick(p0: View?) {
        when(p0){
            button1 -> {
                toast("button1")
            }
            button2 -> {
                toast("button2")
            }
            button3 -> {
                toast("button3")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setToolbar()
        changeViewsFonts()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        Util.changeFontOfNavigation(this, nav_view)

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@HomeActivity, Constants.FONT_REGULAR, toolbarTitle,
                text1, text2, text3)
    }

    fun setToolbar(){
        Util.manipulateToolbar(this@HomeActivity, toolbar, 0, {}, true)
    }
}
