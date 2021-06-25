package com.example.flightgear_controller_application.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.flightgear_controller_application.R
import com.example.flightgear_controller_application.model.FlightGearControllerModel
import com.example.flightgear_controller_application.model.IFlightGearControllerModel
import com.example.flightgear_controller_application.viewmodel.GeneralViewModel
import com.example.flightgear_controller_application.viewmodel.RudderViewModel
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var generalVM: GeneralViewModel
        private set
    var rudderVM: RudderViewModel
        private set

    init {
        val model: IFlightGearControllerModel = FlightGearControllerModel()

        generalVM = GeneralViewModel(model)
        rudderVM = RudderViewModel(model)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<SeekBar>(R.id.seekbar1).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                rudderVM.throttle = p1.toFloat()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        findViewById<SeekBar>(R.id.seekbar2).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                rudderVM.rudder = p1.toFloat()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        if (generalVM.isConnected) {
            runBlocking {
                generalVM.disconnectFromFG()
            }
        }
    }

    fun onClickConnectionButton(view: View) {
        val ip = findViewById<EditText>(R.id.ip_text).text
        val port = findViewById<EditText>(R.id.port_text).text

        if (ip.isEmpty() || port.isEmpty()) {
            Toast.makeText(applicationContext, "IP or Port were not supplied", Toast.LENGTH_SHORT).show()

            return
        }

        GlobalScope.launch(Dispatchers.Main) {

            if (generalVM.isConnected) {
                generalVM.disconnectFromFG()
            }

            try {
                generalVM.connectToFG(ip.toString(), port.toString().toInt())
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Couldn't establish connection with the host", Toast.LENGTH_SHORT).show()
                }

                return@launch
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Connection established\nReady to use", Toast.LENGTH_SHORT).show()
            }

            generalVM.render()
        }
    }
}