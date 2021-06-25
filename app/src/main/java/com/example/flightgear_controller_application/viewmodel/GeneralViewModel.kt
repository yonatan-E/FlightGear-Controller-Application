package com.example.flightgear_controller_application.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flightgear_controller_application.model.IFlightGearControllerModel
import kotlinx.coroutines.Job

class GeneralViewModel(private val fgModel: IFlightGearControllerModel) : ViewModel() {

    fun connectToFG(ip: String, port: Int) : Job {
        return fgModel.connectToFG(ip, port)
    }

    fun disconnectFromFG() : Job {
        return fgModel.disconnectFromFG()
    }

    fun render() : Job {
        return fgModel.render()
    }
}