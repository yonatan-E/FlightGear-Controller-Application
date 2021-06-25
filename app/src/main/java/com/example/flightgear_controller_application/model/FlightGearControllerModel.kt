package com.example.flightgear_controller_application.model

import android.util.Log
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.Socket

class FlightGearControllerModel : IFlightGearControllerModel {

    private lateinit var _sock: Socket

    private val sendingInterval: Long = 5

    override fun connectToFG(ip: String, port: Int) : Job {
        return GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                _sock = Socket(ip, port)
            }
        }
    }

    override fun disconnectFromFG() : Job {
        return GlobalScope.launch(Dispatchers.IO) {
            _sock.close()
        }
    }

    override fun render() : Job {
        return GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                val job = GlobalScope.launch(Dispatchers.IO) {
                    _sock.getOutputStream().write(null)
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