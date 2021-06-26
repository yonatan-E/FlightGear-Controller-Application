package com.example.flightgear_controller_application.model

import kotlinx.coroutines.Job

interface IFlightGearControllerModel {

    /**
     * Connect to the flight gear server in the specified ip and port
     */
    suspend fun connectToFG(ip: String, port: Int)

    /**
     * Disconnect from the flight gear server, if connected
     */
    suspend fun disconnectFromFG()

    /**
     * Check if is connected to the flight gear server
     */
    fun isConnected() : Boolean

    /**
     * Start sending data to the flight gear at every time interval
     */
    fun render(sendingInterval: Long) : Job

    // current flight data
    var aileron: Float
    var elevator: Float
    var throttle: Float
    var rudder: Float

}