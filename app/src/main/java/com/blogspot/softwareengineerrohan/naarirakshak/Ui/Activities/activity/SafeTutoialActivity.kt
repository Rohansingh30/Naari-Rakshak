package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivitySafeTutoialBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SafeTutoialActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySafeTutoialBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)





        binding.apply {
            exTv.setAnimationDuration(750L)
            exTv.setInterpolator(OvershootInterpolator())

            exTv2.setAnimationDuration(750L)
            exTv2.setInterpolator(OvershootInterpolator())

            seeMoreTv.setOnClickListener {
                if (exTv.isExpanded) {
                    exTv.collapse()
                    seeMoreTv.text = "See more"

                } else {
                    exTv.expand()
                    seeMoreTv.text = "See less"
                }
            }
            seeMoreTv2.setOnClickListener {
                if (exTv2.isExpanded) {
                    exTv2.collapse()
                    seeMoreTv2.text = "See more"
                } else {
                    exTv2.expand()
                    seeMoreTv2.text = "See less"
                }
            }
        }
    }
}