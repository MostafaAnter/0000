package travel.com.touristesTripResults.models

import travel.com.touristesTripResults.models.DataItem

data class Paginator(val firstPageUrl: String = "",
                     val path: String = "",
                     val perPage: Int = 0,
                     val total: Int = 0,
                     val data: List<DataItem>?,
                     val lastPage: Int = 0,
                     val lastPageUrl: String = "",
                     val nextPageUrl: String = "",
                     val from: Int = 0,
                     val to: Int = 0,
                     val prevPageUrl: String = "",
                     val currentPage: Int = 0)