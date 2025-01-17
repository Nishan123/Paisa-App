package com.example.paisa.models

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class TransactionModel (
    var transactionId:String ="",
    var title:String="",
    var description:String="",
    var amount:Int=0,
    var isLend:Boolean=false,
    var lendTo:String="",
    var transactionDate:LocalDateTime= LocalDateTime.now(),
    var lastEditDate: LocalDateTime = transactionDate
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()?:"",
        LocalDateTime.parse(parcel.readString(), DateTimeFormatter.ISO_DATE_TIME ),
        LocalDateTime.parse(parcel.readString(),DateTimeFormatter.ISO_DATE_TIME)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(transactionId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(amount)
        parcel.writeByte(if (isLend) 1 else 0)
        parcel.writeString(lendTo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionModel> {
        override fun createFromParcel(parcel: Parcel): TransactionModel {
            return TransactionModel(parcel)
        }

        override fun newArray(size: Int): Array<TransactionModel?> {
            return arrayOfNulls(size)
        }
    }
}