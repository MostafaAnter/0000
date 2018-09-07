package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubCategory(val categoryId: String = "",
                       val updatedAt: String = "",
                       val createdAt: String = "",
                       val id: Int = 0,
                       val text: String? = "",
                       val slug: String? = ""): Parcelable