package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(val updatedAt: String = "",
                   val createdAt: String = "",
                   val id: Int = 0,
                   val text: String = "",
                   val isoCode: String = "",
                   val slug: String = ""): Parcelable