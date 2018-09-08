package travel.com.myBookings.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomTypes(val key: String = "", val value: String = ""): Parcelable