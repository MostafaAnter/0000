package travel.com.rest

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import travel.com.BuildConfig

/**
 * Created by mostafa_anter on 1/1/17.
 */

object ApiClient {
    private val BASE_URL: String = BuildConfig.API_BASE_URL
    private var retrofit: Retrofit? = null


    fun getClient(baseUrl: String = BASE_URL): Retrofit? {
        if (retrofit == null) {
            // set time out rules to 60 sec
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder().addInterceptor(logging)
                    .readTimeout(240, TimeUnit.SECONDS)
                    .connectTimeout(240, TimeUnit.SECONDS)
                    .build()


            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
        }
        return retrofit
    }
}
