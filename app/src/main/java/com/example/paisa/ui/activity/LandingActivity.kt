package com.example.paisa.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paisa.R
import com.example.paisa.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {
    lateinit var landingBinding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        landingBinding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(landingBinding.root)
        landingBinding.appLogo.setImageResource(R.drawable.app_logo)
        landingBinding.textButton.setOnClickListener {
            var intent = Intent(this@LandingActivity, SignupActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}