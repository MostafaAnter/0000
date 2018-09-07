package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripImagesItem(val image: String = "",
                          val tripId: String = "",
                          val order: String = ""): Parcelable