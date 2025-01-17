package com.example.paisa.viewModel

import com.example.paisa.models.TransactionModel
import com.example.paisa.repo.TransactionRepo

class TransactionViewModel(val repo: TransactionRepo) {
    fun addTransaction(
        transaction: TransactionModel,
        callback: (Boolean, String) -> Unit,
    ){
        repo.addTransaction(transaction, callback)
    }

    fun deleteTransaction(transactionId: String, callback: (Boolean, String) -> Unit){
        repo.deleteTransaction(transactionId,callback)
    }

    fun updateTransaction(transactionId: String, data:MutableMap<String, Any>, callback: (Boolean, String) -> Unit)
    {
        repo.updateTransaction(transactionId, data, callback)
    }
    fun getAllTransaction(callback: (Boolean, String, List<TransactionModel>) -> Unit){
        repo.getAllTransaction(callback)
    }

    fun getParticularTransaction(transactionType:String,callback: (Boolean, String, List<TransactionModel>) -> Unit ){
        repo.getParticularTransaction(transactionType, callback)
    }

}