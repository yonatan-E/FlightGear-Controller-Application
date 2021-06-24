package com.example.flightgear_controller_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), JoystickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onJoystickMoved(xPercent: Float, yPercent: Float, id: Int) {
        Log.d("Main Method", "X percent: $xPercent Y percent: $yPercent")
    }
}