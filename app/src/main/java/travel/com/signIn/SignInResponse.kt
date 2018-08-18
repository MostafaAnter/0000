package travel.com.signIn

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class SignInResponse(val item: Item,
                          val code: Int = 0,
                          val message: String = "") : Parcelable, Serializable {
    constructor(source: Parcel) : this(
            source.readParcelable<Item>(Item::class.java.classLoader),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(item, 0)
        writeInt(code)
        writeString(message)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SignInResponse> = object : Parcelable.Creator<SignInResponse> {
            override fun createFromParcel(source: Parcel): SignInResponse = SignInResponse(source)
            override fun newArray(size: Int): Array<SignInResponse?> = arrayOfNulls(size)
        }
    }
}
