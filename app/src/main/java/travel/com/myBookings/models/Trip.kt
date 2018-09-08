package travel.com.myBookings.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trip(val date: String = "",
                val end_date: String = "",
                val created_at: String = "",
                val title: String = "",
                val hotel_dec: String = "",
                val hasParking: String = "",
                val checkOut: String = "",
                val categoryId: String = "",
                val updatedAt: String = "",
                val price: String = "",
                val subCategoryId: String = "",
                val price_chl: String = "",
                val id: Int = 0,
                val important_info: String = "",
                val lat: String = "",
                val start_date: String = "",
                val member_id: String = "",
                val image: String = "",
                val hotel_items: HotelItems,
                val isActive: String = "",
                val lng: String = "",
                val check_in: String = "",
                val price_dbl: String = "",
                val price_tpl: String = "",
                val stars: Int = 0,
                val allow_pets: String = "",
                val has_internet: String = "",
                val cancellation_policy: String = "",
                val countryId: String = "",
                val cityId: String = ""): Parcelable