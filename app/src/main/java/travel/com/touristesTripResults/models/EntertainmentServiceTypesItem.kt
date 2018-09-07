package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntertainmentServiceTypesItem(val pivot: Pivot,
                                         val text: String = ""): Parcelable