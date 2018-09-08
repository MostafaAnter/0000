package travel.com.myBookings.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(val note: String? = "",
                    val trip_id: String? = "",
                    val child_count: String? = "",
                    val room_count: String? = "",
                    val created_at: String? = "",
                    val child_ages: List<ChildAges>?,
                    val payment_method_text: String? = "",
                    val client_id: String? = "",
                    val status_txt: String? = "",
                    val trip: Trip,
                    val room_types: List<RoomTypes>?,
                    val updatedAt: String? = "",
                    val price: String? = "",
                    val id: Int = 0,
                    val adult_count: String? = "",
                    val payment_method: String? = "",
                    val status: String? = ""): Parcelable