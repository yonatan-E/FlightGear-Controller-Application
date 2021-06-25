package com.example.flightgear_controller_application.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
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
    }

    fun onClickConnectionButton(view: View) {
        val ip = findViewById<EditText>(R.id.ip_text).text
        val port = findViewById<EditText>(R.id.port_text).text

        if (ip.isEmpty() || port.isEmpty()) {
            Toast.makeText(applicationContext, "IP or Port were not supplied", Toast.LENGTH_SHORT).show()

            return
        }

        GlobalScope.launch(Dispatchers.Main) {

            try {
                generalVM.connectToFG(ip.toString(), port.toString().toInt()).join()
            }
            catch (e: Exception) {
                Log.d("Socket", "Falied")
                Toast.makeText(applicationContext, "Couldn't connect to the host", Toast.LENGTH_SHORT).show()

                return@launch
            }

            try {
                generalVM.render().join()
                Toast.makeText(applicationContext, "Connection established\nReady to use", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception) {
                Toast.makeText(applicationContext, "Couldn't interact with the host", Toast.LENGTH_SHORT).show()
            }
        }
    }
}