package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.LoginFragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)



        }, 3000)




        val binding = FragmentSplashBinding.inflate(inflater, container, false)

        val animTopImage = AnimationUtils.loadAnimation(requireContext(), R.anim.from_top_image)
        val animBottomText = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_text)

        binding.splashImage.startAnimation(animTopImage)
        binding.splashText.startAnimation(animBottomText)




        return binding.root

    }

}