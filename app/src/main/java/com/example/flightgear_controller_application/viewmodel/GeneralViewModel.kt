package com.example.flightgear_controller_application.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flightgear_controller_application.model.IFlightGearControllerModel

class GeneralViewModel(private val fgModel: IFlightGearControllerModel) : ViewModel() {

    suspend fun connectToFG(ip: String, port: Int) {
        fgModel.connectToFG(ip, port)
    }

    suspend fun disconnectFromFG() {
        fgModel.disconnectFromFG()
    }

    suspend fun render() {
        fgModel.render()
    }
}