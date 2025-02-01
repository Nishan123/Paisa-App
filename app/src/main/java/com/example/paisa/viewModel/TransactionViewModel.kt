package com.example.paisa.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.paisa.models.TransactionModel
import com.example.paisa.repo.TransactionRepo

class TransactionViewModel(val repo: TransactionRepo) {
    fun addTransaction(
        transaction: TransactionModel,
        callback: (Boolean, String) -> Unit,
    ) {
        repo.addTransaction(transaction, callback)
    }

    fun deleteTransaction(transactionId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteTransaction(transactionId, callback)
    }

    var _transactions = MutableLiveData<TransactionModel?>()
    var transactions = MutableLiveData<TransactionModel?>()
        get() = _transactions

    var _allTransaction = MutableLiveData<List<TransactionModel>>()
    var allTransaction = MutableLiveData<List<TransactionModel>>()
        get() = _allTransaction

    var _empty = MutableLiveData<Boolean>()
    var empty = MutableLiveData<Boolean>()
        get() = _empty


    fun updateTransaction(
        transactionId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit,
    ) {
        repo.updateTransaction(transactionId, data, callback)
    }

    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading

    fun getAllTransaction() {
        _loading.value = true
        repo.getAllTransaction { success, message, data ->
            if (success) {

                _allTransaction.value = data
                _loading.value = false
                _empty.value = false


            } else {
                _allTransaction.value = null
                _loading.value = false
                _empty.value = true
            }
        }
    }


    fun getParticularTransaction(
        transactionType: String,
        callback: (Boolean, String, List<TransactionModel>) -> Unit,
    ) {

        _loading.value = true
        repo.getParticularTransaction(transactionType, callback)
    }

}