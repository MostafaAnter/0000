package travel.com.touristesTripsFilter

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CountryItem(val updatedAt: String = "",
                       val createdAt: String = "",
                       val id: Int = 0,
                       val text: String = "",
                       val isoCode: String = "",
                       val slug: String = "") : Parcelable, Serializable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(updatedAt)
        writeString(createdAt)
        writeInt(id)
        writeString(text)
        writeString(isoCode)
        writeString(slug)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CountryItem> = object : Parcelable.Creator<CountryItem> {
            override fun createFromParcel(source: Parcel): CountryItem = CountryItem(source)
            override fun newArray(size: Int): Array<CountryItem?> = arrayOfNulls(size)
        }
    }
}