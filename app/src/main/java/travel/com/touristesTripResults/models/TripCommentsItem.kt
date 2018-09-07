package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripCommentsItem(val tripId: String? = "",
                            val updatedAt: String? = "",
                            val createdAt: String? = "",
                            val client: Client,
                            val comment: String? = "",
                            val id: Int = 0,
                            val clientId: String? = ""): Parcelable