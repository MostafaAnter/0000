package travel.com.touristesCompanies.models

data class CommentResponse(val item: Item,
                           val code: Int = 0,
                           val message: String = "")