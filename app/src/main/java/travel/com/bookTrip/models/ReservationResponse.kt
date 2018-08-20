package travel.com.bookTrip.models

data class ReservationResponse(val item: Item,
                               val code: Int = 0,
                               val message: String = "")