package travel.com.bookTrip.models

data class Item(val note: String = "",
                val tripId: String = "",
                val childCount: String = "",
                val roomCount: String = "",
                val createdAt: String = "",
                val childAges: ChildAges,
                val paymentMethodText: String = "",
                val clientId: Int = 0,
                val statusTxt: String = "",
                val roomTypes: RoomTypes,
                val updatedAt: String = "",
                val id: Int = 0,
                val adultCount: String = "",
                val paymentMethod: String = "")