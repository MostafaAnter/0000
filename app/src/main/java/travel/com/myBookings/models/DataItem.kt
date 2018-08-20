package travel.com.myBookings.models

data class DataItem(val note: String = "",
                    val tripId: String = "",
                    val childCount: String = "",
                    val roomCount: String = "",
                    val createdAt: String = "",
                    val childAges: ChildAges,
                    val paymentMethodText: String = "",
                    val clientId: String = "",
                    val statusTxt: String = "",
                    val trip: Trip,
                    val roomTypes: RoomTypes,
                    val updatedAt: String = "",
                    val price: String = "",
                    val id: Int = 0,
                    val adultCount: String = "",
                    val paymentMethod: String = "",
                    val status: String = "")