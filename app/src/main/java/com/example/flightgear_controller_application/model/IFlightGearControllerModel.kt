package com.example.flightgear_controller_application.model

interface IFlightGearControllerModel {

    suspend fun connectToFG(ip: String, port: Int)
    suspend fun disconnectFromFG()
    suspend fun render()

    var aileron: Int
    var elevator: Int
    var throttle: Int
    var rudder: Int

}