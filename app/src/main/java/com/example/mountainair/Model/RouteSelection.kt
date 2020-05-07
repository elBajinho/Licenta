package com.example.mountainair.Model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class  RouteSelection(var minH: Int, var maxH: Int, var difficulties: ArrayList<String>?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(minH)
        parcel.writeInt(maxH)
        parcel.writeStringList(difficulties)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RouteSelection> {
        override fun createFromParcel(parcel: Parcel): RouteSelection {
            return RouteSelection(parcel)
        }

        override fun newArray(size: Int): Array<RouteSelection?> {
            return arrayOfNulls(size)
        }
    }
}