package com.example.paisa.viewModel

import android.content.Context
import android.net.Uri
import com.example.paisa.models.UserModel
import com.example.paisa.repo.UserRepo
import com.google.firebase.auth.FirebaseUser

class UserViewModel(var repo:UserRepo) {
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit){
        repo.login(email,password,callback)
    }
    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit){
        repo.signup(email,password,callback)
    }
    fun addUserToDb(userId:String, userModel: UserModel,
                    callback: (Boolean, String) -> Unit,
    ){
        repo.addUserToDb(userId, userModel, callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email, callback)
    }
    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }
    fun uploadProfilePic(context: Context, imageUri: Uri, callback: (String?) -> Unit){
       repo.uploadProfilePic(context,imageUri,callback)
    }
}