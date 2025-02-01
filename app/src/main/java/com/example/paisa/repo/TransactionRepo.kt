package com.example.paisa.repo

import com.example.paisa.models.TransactionModel

interface TransactionRepo {
    fun addTransaction(
        transaction: TransactionModel,
        callback: (Boolean, String) -> Unit,
    )

    fun deleteTransaction(transactionId: String, callback: (Boolean, String) -> Unit)

    fun updateTransaction(transactionId: String, data:MutableMap<String, Any>, callback: (Boolean, String) -> Unit)

    fun getAllTransaction(callback: (Boolean, String, List<TransactionModel>?) -> Unit)

    fun getParticularTransaction(transactionType:String,callback: (Boolean, String, List<TransactionModel>) -> Unit )

}