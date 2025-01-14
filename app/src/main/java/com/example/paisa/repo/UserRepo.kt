package com.example.paisa.repo

import com.example.paisa.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface UserRepo {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)
    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit)
    fun addUserToDb(userId:String,userModel: UserModel,
        callback: (Boolean, String) -> Unit,
    )

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit)
    fun getCurrentUser():FirebaseUser?
}