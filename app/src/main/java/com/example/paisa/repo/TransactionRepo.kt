package com.example.paisa.repo

interface TransactionRepo{
    fun addTransaction(transactionId:String, title:String, description:String, amount:Float, isTransfer:Boolean, transferTo:String?, callback: (Boolean, String) -> Unit)
    fun deleteTransaction(transactionId: String, callback:(Boolean, String)->Unit)
}