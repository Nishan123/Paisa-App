package com.example.paisa.repo

import com.example.paisa.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRepoImpl:UserRepo {

    var auth:FirebaseAuth = FirebaseAuth.getInstance()
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    var db:DatabaseReference = database.reference.child("users")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "Login in Successful")
            }else{
                callback(false, "Login failed")
            }
        }
    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "Sign up successful", getCurrentUser()?.uid.toString())
            }else{
                callback(false, "Sign up failed", "")
            }
        }
    }

    override fun addUserToDb(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit,
    ) {
        db.child(userId).setValue(userModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "User add to database")
            }else{
                callback(false, "User failed to add in database")
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}