package com.example.mountainair.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

data class Filters(var activities : ArrayList<String>, var date : Date, var gs : GeographicSelection?, var rs : RouteSelection?, var ws : WheatherSelection?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readSerializable() as Date,
        parcel.readParcelable(GeographicSelection::class.java.classLoader),
        parcel.readParcelable(RouteSelection::class.java.classLoader),
        parcel.readParcelable(WheatherSelection::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(activities)
        parcel.writeSerializable(date)
        parcel.writeParcelable(gs, flags)
        parcel.writeParcelable(rs, flags)
        parcel.writeParcelable(ws, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Filters> {
        override fun createFromParcel(parcel: Parcel): Filters {
            return Filters(parcel)
        }

        override fun newArray(size: Int): Array<Filters?> {
            return arrayOfNulls(size)
        }
    }
}