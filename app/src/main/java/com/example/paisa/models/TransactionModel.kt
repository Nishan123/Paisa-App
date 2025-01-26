package com.example.paisa.models

import android.os.Parcel
import android.os.Parcelable

data class TransactionModel (
    var transactionId:String ="",
    var title:String="",
    var description:String="",
    var amount:Int=0,
    var transactionType:String="",
    var isLend:Boolean=false,
    var lendTo:String="",
    var transactionDate:String="",
    var transactionTime:String="",
    var lastEditDate: String="",
    var lastEditTime: String=""

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt()?:0,
        parcel.readString()?:"",
        parcel.readByte() != 0.toByte(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(transactionId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(amount)
        parcel.writeString(transactionType)
        parcel.writeByte(if (isLend) 1 else 0)
        parcel.writeString(lendTo)
        parcel.writeString(transactionDate)
        parcel.writeString(transactionTime)
        parcel.writeString(lastEditDate)
        parcel.writeString(lastEditTime)
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