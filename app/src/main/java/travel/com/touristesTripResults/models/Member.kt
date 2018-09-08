package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Member(val image: String? = "",
                  val isActive: String? = "",
                  val lng: String? = "",
                  val mobile: String? = "",
                  val about: String? = "",
                  val created_at: String? = "",
                  val adress: String? = "",
                  val stars: Int = 0,
                  val updatedAt: String? = "",
                  val name: String? = "",
                  val logo: String? = "",
                  val id: Int = 0,
                  val member_comments: List<TripCommentsItem>? = null,
                  val email: String? = "",
                  val lat: String? = "",
                  val countryId: String? = "",
                  val cityId: String? = ""): Parcelable