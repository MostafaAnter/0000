package travel.com.touristesTripsFilter.models

data class TripCategoriesResponse(val code: Int = 0,
                                  val message: String = "",
                                  val items: List<CategoryItem>?)