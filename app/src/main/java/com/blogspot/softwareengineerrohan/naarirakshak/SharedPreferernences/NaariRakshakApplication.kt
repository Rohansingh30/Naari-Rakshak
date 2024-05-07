package com.blogspot.softwareengineerrohan.naarirakshak.SharedPreferernences

import android.app.Application

class NaariRakshakApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPref.init(this)
    }

}