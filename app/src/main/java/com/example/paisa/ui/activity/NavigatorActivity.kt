package com.example.paisa.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.paisa.R
import com.example.paisa.databinding.ActivityNavigatorBinding
import com.example.paisa.ui.fragment.HomeFragment
import com.example.paisa.ui.fragment.ProfileFragment
import com.example.paisa.ui.fragment.StatsFragment

class NavigatorActivity : AppCompatActivity() {
    lateinit var navigatorBinding: ActivityNavigatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        navigatorBinding = ActivityNavigatorBinding.inflate(layoutInflater)
        setContentView(navigatorBinding.root)
        replaceFragment(HomeFragment())
        navigatorBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.stats -> replaceFragment(StatsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {

                }
            }
            true
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}