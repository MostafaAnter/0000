package travel.com.myBookings.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelItems(val hasParking: String = "",
                      val hasInternet: String = "",
                      val allowPets: String = ""): Parcelable