package travel.com.splashScreen

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import travel.com.homeScreen.HomeActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg params: Void): Void? {
                try {
                    Thread.sleep(2000)
                } catch (e: Exception) {
                }
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()

            }
        }.execute()
    }
}
