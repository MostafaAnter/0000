package travel.com.touristesCompaniesDetails

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class CommentsModel(val name: String, val comment: String, val image: String, val date: String? = "2018-06-19 08:36:55"){
    val dateAfterTransformation: String
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            var date1: Date? = null
            try {
                date1 = formatter.parse(this.date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val dateFormat = SimpleDateFormat("yyyy/MM/dd")
            return dateFormat.format(date1)
        }
}
