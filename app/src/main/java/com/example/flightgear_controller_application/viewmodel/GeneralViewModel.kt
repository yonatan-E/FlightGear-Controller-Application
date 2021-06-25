package com.example.flightgear_controller_application.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flightgear_controller_application.model.IFlightGearControllerModel
import kotlinx.coroutines.Job

class GeneralViewModel(private val fgModel: IFlightGearControllerModel) : ViewModel() {

    var isConnected: Boolean = false
        private set

    suspend fun connectToFG(ip: String, port: Int) {
        fgModel.connectToFG(ip, port)

        isConnected = true
    }

    suspend fun disconnectFromFG() {
        fgModel.disconnectFromFG()

        isConnected = false
    }

    fun render() : Job {
        return fgModel.render()
    }
}