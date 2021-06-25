package com.example.flightgear_controller_application.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.flightgear_controller_application.model.IFlightGearControllerModel

class RudderViewModel(private val fgModel: IFlightGearControllerModel) : ViewModel() {

    var aileron: Float
        get() = fgModel.aileron
        set(value) { fgModel.aileron = value }
    var elevator: Float
        get() = fgModel.elevator
        set(value) { fgModel.elevator = value }
    var throttle: Float
        get() = fgModel.throttle
        set(value) { fgModel.throttle = value / 100 }
    var rudder: Float
        get() = fgModel.rudder
        set(value) { fgModel.rudder = (value - 50) / 100 }
}