package com.example.paisa.repo

import com.example.paisa.models.TransactionModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TransactionRepoImpl:TransactionRepo {
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    var db:DatabaseReference = database.reference.child("transactions")


    override fun addTransaction(
        transaction: TransactionModel,
        callback: (Boolean, String) -> Unit,
    ) {
        val id = db.push().key.toString()
        transaction.transactionId = id

        db.child(id).setValue(transaction).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "Transaction added successfully")
            }else
            {
                callback(false, "Failed to add transaction !")
            }
        }
    }

    override fun deleteTransaction(transactionId: String, callback: (Boolean, String) -> Unit) {
        db.child(transactionId).removeValue().addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "Transaction deleted successfully")
            }else{
                callback(true, "Failed to delete transaction")

            }
        }
    }

    override fun updateTransaction(
        transactionId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit,
    ) {
       db.child(transactionId).updateChildren(data).addOnCompleteListener {
           if(it.isSuccessful){
               callback(true, "Transaction edited successfully")
           }else{
               callback(true, "Failed to edit transaction")

           }
       }
    }

    override fun getAllTransaction(callback: (Boolean, String, List<TransactionModel>) -> Unit) {
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var transactions = mutableListOf<TransactionModel>()
                    for(eachData in snapshot.children){
                        var model = eachData.getValue(TransactionModel::class.java)

                        if(model!=null){
                            transactions.add(model)
                        }
                    }
                    callback(true, "Transactions are here !", transactions)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false,error.message, emptyList())
            }
        })
    }

    override fun getParticularTransaction(
        transactionType: String,
        callback: (Boolean, String, List<TransactionModel>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }


}