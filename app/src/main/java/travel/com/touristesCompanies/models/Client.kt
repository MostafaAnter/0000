package travel.com.touristesCompanies.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Client(val authorization: String = "",
                  val image: String = "",
                  val isActive: String = "",
                  val emailActive: String = "",
                  val deviceId: String = "",
                  val updatedAt: String = "",
                  val name: String = "",
                  val mobile: String = "",
                  val createdAt: String = "",
                  val deviceType: String = "",
                  val id: Int = 0,
                  val email: String = ""): Parcelable