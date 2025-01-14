package com.example.paisa.repo

import com.example.paisa.models.TransactionModel

interface TransactionRepo {
    fun addTransaction(
        transactionId: String,
        userId: String,
        transaction: TransactionModel,
        callback: (Boolean, String) -> Unit,
    )

    fun deleteTransaction(userId: String,transactionId: String, callback: (Boolean, String) -> Unit)
}