package travel.com.touristesTripDetail.models

data class Item(val tripId: String = "",
                val updatedAt: String = "",
                val createdAt: String = "",
                val client: Client,
                val comment: String = "",
                val id: Int = 0,
                val clientId: String = "")