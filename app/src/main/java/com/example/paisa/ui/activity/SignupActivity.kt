package com.example.paisa.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paisa.R
import com.example.paisa.databinding.ActivitySignupBinding
import com.example.paisa.models.UserModel
import com.example.paisa.repo.UserRepo
import com.example.paisa.repo.UserRepoImpl
import com.example.paisa.viewModel.UserViewModel

class SignupActivity() : AppCompatActivity() {
    lateinit var signupBinding: ActivitySignupBinding
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        signupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)

        signupBinding.textButton.setOnClickListener {
            var intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        userViewModel = UserViewModel(UserRepoImpl())

        signupBinding.signupButton.setOnClickListener {
            var firstName = signupBinding.firstNameText.text.toString()
            var lastName = signupBinding.lastNameText.text.toString()
            var email = signupBinding.emailText.text.toString()
            var password = signupBinding.passwordText.text.toString()

            userViewModel.signup(email, password) { success, message, userId ->
                if (success) {
                    var userModel = UserModel(
                        userId,
                        firstName,
                        lastName,
                        email,
                    )
                    userViewModel.addUserToDb(
                        userId,
                        userModel,
                    ) { dbSuccess, dbMessage ->
                        if (dbSuccess) {
                            Toast.makeText(
                                this@SignupActivity,
                                dbMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@SignupActivity,
                                dbMessage, Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        message, Toast.LENGTH_LONG
                    ).show()
                }

            }


        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}