package com.example.paisa.repo

import android.content.Context
import android.net.Uri
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.paisa.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.InputStream
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import android.provider.OpenableColumns
import android.database.Cursor



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

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dz56tb6s7",
            "api_key" to "128195668997422",
            "api_secret" to "218j4vSbVWOrASjVbCQSI6uTbrw"
        )
    )

    override fun uploadProfilePic(context: Context, uri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                var fileName = getFileNameFromUri(context, uri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }

}