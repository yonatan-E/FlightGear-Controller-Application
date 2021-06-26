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
import com.example.flightgear_controller_application.viewmodel.NetworkingViewModel
import com.example.flightgear_controller_application.viewmodel.RudderViewModel
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var networkingVM: NetworkingViewModel
        private set
    var rudderVM: RudderViewModel
        private set

    init {
        val model: IFlightGearControllerModel = FlightGearControllerModel()

        networkingVM = NetworkingViewModel(model)
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

        if (networkingVM.isConnected()) {
            runBlocking {
                networkingVM.disconnectFromFG()
            }
        }
    }

    fun onClickConnectionButton(view: View) {
        // getting the given ip and the port
        val ip = findViewById<EditText>(R.id.ip_text).text
        val port = findViewById<EditText>(R.id.port_text).text

        // if ip or port were not supplied then exiting
        if (ip.isEmpty() || port.isEmpty()) {
            Toast.makeText(applicationContext, "IP or Port were not supplied", Toast.LENGTH_SHORT).show()

            return
        }

        // launching a coroutine on the main thread
        GlobalScope.launch(Dispatchers.Main) {

            // disconnecting from another flight gear, if connected
            if (networkingVM.isConnected()) {
                networkingVM.disconnectFromFG()
            }

            // trying to connect to the given host
            try {
                networkingVM.connectToFG(ip.toString(), port.toString().toInt())
            }
            catch (e: Exception) {
                // printing error message in case that couldn't establish a connection with the host
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Couldn't establish connection with host $ip on port $port", Toast.LENGTH_SHORT).show()
                }

                return@launch
            }

            // printing success message in case that a connection with the host was established
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Connection established with host $ip on port $port\nReady to use", Toast.LENGTH_SHORT).show()
            }

            // starting sending the flight data corresponding the user action.
            // joining the render coroutine will block the current coroutine until the rendering has stopped because
            // of the connection with the host was closed
            networkingVM.render().join()

            // printing a message that indicates that the connection with the host was closed
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Disconnected from host $ip", Toast.LENGTH_SHORT).show()
            }
        }
    }
}