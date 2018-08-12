package travel.com.touristesTripsFilter

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CitiesResponse(val code: Int = 0,
                          val message: String = "",
                          val items: List<CityItem>?) : Parcelable, Serializable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            ArrayList<CityItem>().apply { source.readList(this, CityItem::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(code)
        writeString(message)
        writeList(items)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CitiesResponse> = object : Parcelable.Creator<CitiesResponse> {
            override fun createFromParcel(source: Parcel): CitiesResponse = CitiesResponse(source)
            override fun newArray(size: Int): Array<CitiesResponse?> = arrayOfNulls(size)
        }
    }
}