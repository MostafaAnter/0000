package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodTypesItem(val pivot: Pivot,
                         val text: String = ""): Parcelable