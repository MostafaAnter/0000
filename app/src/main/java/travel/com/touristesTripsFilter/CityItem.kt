package travel.com.touristesTripsFilter

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CityItem(val updatedAt: String = "",
                    val createdAt: String = "",
                    val id: Int = 0,
                    val text: String = "",
                    val slug: String = "",
                    val countryId: String = "") : Parcelable, Serializable {
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
        writeString(slug)
        writeString(countryId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CityItem> = object : Parcelable.Creator<CityItem> {
            override fun createFromParcel(source: Parcel): CityItem = CityItem(source)
            override fun newArray(size: Int): Array<CityItem?> = arrayOfNulls(size)
        }
    }
}