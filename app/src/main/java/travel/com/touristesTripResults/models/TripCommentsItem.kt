package travel.com.touristesTripResults.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripCommentsItem(val trip_id: String? = "",
                            val updated_at: String? = "",
                            val created_at: String? = "",
                            val client: Client,
                            val comment: String? = "",
                            val id: Int = 0,
                            val client_id: String? = ""): Parcelable