package travel.com.touristesCompanies.models

import travel.com.touristesTripResults.models.Member

data class Paginator(val firstPageUrl: String? = "",
                     val path: String? = "",
                     val perPage: Int = 0,
                     val total: Int = 0,
                     val data: List<Member>?,
                     val lastPage: Int = 0,
                     val lastPageUrl: String? = "",
                     val next_page_url: String? = "",
                     val from: Int = 0,
                     val to: Int = 0,
                     val prevPageUrl: String? = "",
                     val currentPage: Int = 0)