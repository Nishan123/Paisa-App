package com.example.paisa.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paisa.R
import com.example.paisa.databinding.FragmentHomeBinding
import com.example.paisa.ui.activity.AddTransaction


class HomeFragment : Fragment() {

lateinit var homeBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        homeBinding=FragmentHomeBinding.inflate(inflater,container,false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.fab.setOnClickListener {
            var intent = Intent(requireContext(), AddTransaction::class.java)
            startActivity(intent)
        }
    }

}