package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.AppScreenInfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivityAppInfoBinding

class AppInfoActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityAppInfoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnPolicy.setOnClickListener {
startActivity(Intent(this,PolicyActivity::class.java))
        }



    }
}