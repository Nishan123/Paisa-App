package com.example.paisa.repo

import com.example.paisa.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class UserRepoImpl:UserRepo {
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()
    private var db:FirebaseFirestore=FirebaseFirestore.getInstance()

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Sign in successful")
            }else{
                callback(false,"Error occurred: ${it.exception?.message.toString()}")
            }
        }
    }

    override fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
       auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
           if(it.isSuccessful){
               callback(true, "Account created successfully", auth.currentUser?.uid.toString())
           }else{
               callback(false, "Error: ${it.exception?.message.toString()}","")
           }
       }
    }

    override fun addUserToDb(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit,
    ) {
        db.collection("users").document(userId).set(userModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "User registered successfully !")
            }else{
                callback(false,"Error: ${it.exception?.message.toString()}")
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