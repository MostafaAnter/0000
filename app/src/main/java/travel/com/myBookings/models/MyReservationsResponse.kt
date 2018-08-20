package travel.com.myBookings.models

data class MyReservationsResponse(val code: Int = 0,
                                  val message: String = "",
                                  val paginator: Paginator)