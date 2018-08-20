package travel.com.touristesCompanies.models

data class Item(val memberId: String = "",
                val updatedAt: String = "",
                val createdAt: String = "",
                val client: Client,
                val comment: String = "",
                val id: Int = 0,
                val clientId: String = "")