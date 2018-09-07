package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(val updatedAt: String = "",
                val createdAt: String = "",
                val id: Int = 0,
                val text: String = "",
                val slug: String = "",
                val countryId: String = ""): Parcelable