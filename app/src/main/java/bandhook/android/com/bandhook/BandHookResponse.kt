package bandhook.android.com.bandhook

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@SuppressLint("ParcelCreator")
class BandHookResponse() : Parcelable {

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        image = parcel.readString()
        price = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BandHookResponse> {
        override fun createFromParcel(parcel: Parcel): BandHookResponse {
            return BandHookResponse(parcel)
        }

        override fun newArray(size: Int): Array<BandHookResponse?> {
            return arrayOfNulls(size)
        }
    }

}