package com.example.flightgear_controller_application.model

import kotlinx.coroutines.Job

interface IFlightGearControllerModel {

    suspend fun connectToFG(ip: String, port: Int)
    suspend fun disconnectFromFG()
    fun render() : Job

    var aileron: Float
    var elevator: Float
    var throttle: Float
    var rudder: Float

}