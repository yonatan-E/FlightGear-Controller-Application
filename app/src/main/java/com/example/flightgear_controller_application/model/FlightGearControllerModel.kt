package com.example.flightgear_controller_application.model

import android.util.Log
import kotlinx.coroutines.*
import java.lang.Exception
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

    override fun render() : Job {
        return GlobalScope.launch(Dispatchers.IO) {
            val stream =  _sock.getOutputStream()

            while (true) {
                val job = GlobalScope.launch(Dispatchers.IO) {
                    stream.write("set /controls/flight/aileron $aileron\r\n".toByteArray())
                    stream.write("set /controls/flight/elevator $elevator\r\n".toByteArray())
                    stream.write("set /controls/flight/throttle $throttle\r\n".toByteArray())
                    stream.write("set /controls/flight/rudder $rudder\r\n".toByteArray())
                    stream.flush()
                }

                delay(sendingInterval)
                job.join()
            }
        }
    }

    override var aileron: Float = 0F
    override var elevator: Float = 0F
    override var throttle: Float = 0F
    override var rudder: Float = 0F
}