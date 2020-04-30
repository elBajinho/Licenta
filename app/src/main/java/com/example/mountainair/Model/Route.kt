package com.example.mountainair.Model

import android.os.Parcel
import android.os.Parcelable

data class Route(var Image : String, var Location : String, var Activities : String, var Description : String, var Time : String, var Level : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Image)
        parcel.writeString(Location)
        parcel.writeString(Activities)
        parcel.writeString(Description)
        parcel.writeString(Time)
        parcel.writeString(Level)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Route> {
        override fun createFromParcel(parcel: Parcel): Route {
            return Route(parcel)
        }

        override fun newArray(size: Int): Array<Route?> {
            return arrayOfNulls(size)
        }
    }
}