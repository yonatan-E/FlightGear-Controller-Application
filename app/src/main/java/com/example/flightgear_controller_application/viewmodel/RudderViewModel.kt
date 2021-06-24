package com.example.flightgear_controller_application.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flightgear_controller_application.model.IFlightGearControllerModel

class RudderViewModel(private val fgModel: IFlightGearControllerModel) : ViewModel() {

    var aileron: Int
        get() = fgModel.aileron
        set(value) { fgModel.aileron = value }
    var elevator: Int
        get() = fgModel.elevator
        set(value) { fgModel.elevator = value }
    var throttle: Int
        get() = fgModel.throttle
        set(value) { fgModel.throttle = value }
    var rudder: Int
        get() = fgModel.rudder
        set(value) { fgModel.rudder = value }
}