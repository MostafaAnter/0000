package travel.com.touristesTripsFilter

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CountriesResponse(val code: Int = 0,
                             val message: String = "",
                             val items: List<CountryItem>?) : Parcelable, Serializable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.createTypedArrayList(CountryItem.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(code)
        writeString(message)
        writeTypedList(items)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CountriesResponse> = object : Parcelable.Creator<CountriesResponse> {
            override fun createFromParcel(source: Parcel): CountriesResponse = CountriesResponse(source)
            override fun newArray(size: Int): Array<CountriesResponse?> = arrayOfNulls(size)
        }
    }
}