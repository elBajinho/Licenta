package com.example.mountainair.Model

import android.os.Parcel
import android.os.Parcelable

data class WheatherSelection (var minW : Int, var maxW : Int, var minT : Int, var maxT: Int, var rain : Boolean, var foog : Boolean):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(minW)
        parcel.writeInt(maxW)
        parcel.writeInt(minT)
        parcel.writeInt(maxT)
        parcel.writeByte(if (rain) 1 else 0)
        parcel.writeByte(if (foog) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WheatherSelection> {
        override fun createFromParcel(parcel: Parcel): WheatherSelection {
            return WheatherSelection(parcel)
        }

        override fun newArray(size: Int): Array<WheatherSelection?> {
            return arrayOfNulls(size)
        }
    }
}