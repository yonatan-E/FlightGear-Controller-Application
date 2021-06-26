package com.example.flightgear_controller_application.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flightgear_controller_application.model.IFlightGearControllerModel
import kotlinx.coroutines.Job

class NetworkingViewModel(private val fgModel: IFlightGearControllerModel) : ViewModel() {

    /**
     * Connect to the flight gear server in the specified ip and port
     */
    suspend fun connectToFG(ip: String, port: Int) {
        fgModel.connectToFG(ip, port)
    }

    /**
     * Disconnect from the flight gear server, if connected
     */
    suspend fun disconnectFromFG() {
        fgModel.disconnectFromFG()
    }

    /**
     * Check if is connected to the flight gear server
     */
    fun isConnected() : Boolean {
        return fgModel.isConnected()
    }

    /**
     * Start sending data to the flight gear at every time interval
     */
    fun render() : Job {
        return fgModel.render(5)
    }
}