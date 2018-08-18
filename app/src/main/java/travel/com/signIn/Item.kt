package travel.com.signIn

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Item(val authorization: String = "",
                val image: String = "",
                val isActive: String = "",
                val emailActive: String = "",
                val deviceId: String = "",
                val updatedAt: String = "",
                val name: String = "",
                val mobile: String = "",
                val createdAt: String = "",
                val deviceType: String = "",
                val id: Int = 0,
                val email: String = "") : Parcelable, Serializable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(authorization)
        writeString(image)
        writeString(isActive)
        writeString(emailActive)
        writeString(deviceId)
        writeString(updatedAt)
        writeString(name)
        writeString(mobile)
        writeString(createdAt)
        writeString(deviceType)
        writeInt(id)
        writeString(email)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Item> = object : Parcelable.Creator<Item> {
            override fun createFromParcel(source: Parcel): Item = Item(source)
            override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
        }
    }
}