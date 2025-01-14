package com.example.paisa.repo

import com.example.paisa.models.TransactionModel
import com.google.firebase.firestore.FirebaseFirestore

class TransactionRepoImpl : TransactionRepo {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun addTransaction(
        transactionId: String,
        userId: String,
        transaction: TransactionModel,
        callback: (Boolean, String) -> Unit,
    ) {
        db.collection("users").document(userId).collection("transactions").document(transactionId)
            .set(transaction)

    }

    override fun deleteTransaction(
        userId: String,
        transactionId: String,
        callback: (Boolean, String) -> Unit,
    ) {
        db.collection("users").document(userId).collection("transactions").document(transactionId)
            .delete()
    }

}