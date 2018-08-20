package travel.com.touristesTripResults.models

import travel.com.touristesTripResults.models.Paginator

data class SearchResults(val code: Int = 0,
                         val message: String = "",
                         val paginator: Paginator)