package com.example.mountainair.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class GeographicSelection(var Carphats: String, var Mountains : String, var Peak : String, var Judet : String ) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Carphats)
        parcel.writeString(Mountains)
        parcel.writeString(Peak)
        parcel.writeString(Judet)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeographicSelection> {
        override fun createFromParcel(parcel: Parcel): GeographicSelection {
            return GeographicSelection(parcel)
        }

        override fun newArray(size: Int): Array<GeographicSelection?> {
            return arrayOfNulls(size)
        }
    }
}