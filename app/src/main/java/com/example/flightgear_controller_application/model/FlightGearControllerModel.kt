package com.example.flightgear_controller_application.model

import kotlinx.coroutines.*
import java.net.Socket

class FlightGearControllerModel : IFlightGearControllerModel {

    private var _sock: Socket = Socket()

    override suspend fun connectToFG(ip: String, port: Int) {
        withContext(Dispatchers.IO) {
            _sock = Socket(ip, port)
        }
    }

    override suspend fun disconnectFromFG() {
        if (!_sock.isConnected) {
            return
        }

        withContext(Dispatchers.IO) {
            _sock.close()
        }
    }

    override fun isConnected() : Boolean {
        return _sock.isConnected
    }

    override fun render(sendingInterval: Long) : Job {
        // launch a coroutine on the IO thread
        return GlobalScope.launch(Dispatchers.IO) {
            val stream =  _sock.getOutputStream()

            while (true) {
                if (!_sock.isConnected) {
                    break
                }

                // launching another coroutine on the IO thread for sending the data
                val sendingJob = launch(Dispatchers.IO) {
                    try {
                        stream.write("set /controls/flight/aileron $aileron\r\n".toByteArray())
                        stream.write("set /controls/flight/elevator $elevator\r\n".toByteArray())
                        stream.write("set /controls/engines/current-engine/throttle $throttle\r\n".toByteArray())
                        stream.write("set /controls/flight/rudder $rudder\r\n".toByteArray())
                        stream.flush()
                    }
                    catch (e: Exception) {}
                }

                // delaying for the given time interval
                delay(sendingInterval)
                // blocking the coroutine until the sending coroutine has finished
                sendingJob.join()
            }
        }
    }

    override var aileron: Float = 0F
    override var elevator: Float = 0F
    override var throttle: Float = 0F
    override var rudder: Float = 0F
}