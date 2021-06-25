package com.example.flightgear_controller_application.model

import kotlinx.coroutines.Job

interface IFlightGearControllerModel {

    fun connectToFG(ip: String, port: Int) : Job
    fun disconnectFromFG() : Job
    fun render() : Job

    var aileron: Float
    var elevator: Float
    var throttle: Float
    var rudder: Float

}