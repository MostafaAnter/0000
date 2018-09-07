package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Member(val image: String? = "",
                  val isActive: String? = "",
                  val lng: String? = "",
                  val mobile: String? = "",
                  val about: String? = "",
                  val createdAt: String? = "",
                  val adress: String? = "",
                  val stars: Int = 0,
                  val updatedAt: String? = "",
                  val name: String? = "",
                  val logo: String? = "",
                  val id: Int = 0,
                  val email: String? = "",
                  val lat: String? = "",
                  val countryId: String? = "",
                  val cityId: String? = ""): Parcelable