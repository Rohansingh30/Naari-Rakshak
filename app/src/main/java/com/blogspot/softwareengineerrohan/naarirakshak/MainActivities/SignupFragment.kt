package com.blogspot.softwareengineerrohan.naarirakshak.MainActivities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.signupBtn.setOnClickListener {

            findNavController().navigate(R.id.action_signupFragment_to_loginFragment2)
        }



        return binding.root

    }

}