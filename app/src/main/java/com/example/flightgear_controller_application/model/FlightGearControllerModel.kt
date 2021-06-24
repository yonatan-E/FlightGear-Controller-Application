package com.example.flightgear_controller_application.model

import kotlinx.coroutines.*
import java.net.Socket

class FlightGearControllerModel : IFlightGearControllerModel {

    private lateinit var _sock: Socket

    private val sendingInterval: Long = 5

    override suspend fun connectToFG(ip: String, port: Int) {
        withContext(Dispatchers.IO) {
            _sock = Socket(ip, port)
        }
    }

    override suspend fun disconnectFromFG() {
        withContext(Dispatchers.IO) {
            _sock.close()
        }
    }

    override suspend fun render() {
        while (true) {
            val job = GlobalScope.launch(Dispatchers.IO) {
                _sock.getOutputStream().write(null)
            }

            delay(sendingInterval)
            job.join()
        }
    }

    override var aileron: Int = 0
    override var elevator: Int = 0
    override var throttle: Int = 0
    override var rudder: Int = 0
}