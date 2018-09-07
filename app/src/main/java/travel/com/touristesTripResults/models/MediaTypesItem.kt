package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaTypesItem(val pivot: Pivot,
                          val text: String = ""): Parcelable