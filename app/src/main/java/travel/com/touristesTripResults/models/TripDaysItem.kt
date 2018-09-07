package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripDaysItem(val tripId: String = "",
                        val day: String = "",
                        val desc: String = ""): Parcelable