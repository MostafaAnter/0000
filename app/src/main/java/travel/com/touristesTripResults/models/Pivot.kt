package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pivot(val tripId: String = "",
                 val mediaTypeId: String = ""): Parcelable