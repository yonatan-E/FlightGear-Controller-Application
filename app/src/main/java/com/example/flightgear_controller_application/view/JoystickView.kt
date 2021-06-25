package com.example.flightgear_controller_application.view

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.flightgear_controller_application.viewmodel.RudderViewModel
import kotlin.math.pow
import kotlin.math.sqrt

class JoystickView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), SurfaceHolder.Callback, View.OnTouchListener {

    private var rudderVM: RudderViewModel
    private var centerX: Float = 0.0f
    private var centerY: Float = 0.0f
    private var baseRadius: Float = 0.0f
    private var hatRadius: Float = 0.0f

    init {
        holder.addCallback(this)
        setOnTouchListener(this)

        rudderVM = (context as MainActivity).rudderVM
    }

    private fun setupDimensions(){
        this.centerX = (width/2).toFloat()
        this.centerY = (height/2).toFloat()
        this.baseRadius = (Math.min(width, height) / 2.5).toFloat()
        this.hatRadius = (Math.min(width, height) / 5).toFloat()
    }

    private fun drawJoystick(newX: Float, newY: Float){
        if(holder.surface.isValid){
            val canvas = this.holder.lockCanvas()
            val colors = Paint()

            canvas.drawARGB(255, 255, 255, 255)
            colors.setARGB(255, 83, 83 ,83)
            canvas.drawCircle(centerX, centerY, baseRadius, colors)
            colors.setARGB(255, 0, 0, 0)
            canvas.drawCircle(newX, newY, hatRadius, colors)
            this.holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v != null && event != null) {
            if(v == this){
                if(event.action != MotionEvent.ACTION_UP) {
                    val displacement = sqrt(
                        (event.x - centerX).toDouble().pow(2.0) + (event.y - centerY).toDouble()
                            .pow(2.0)
                    )
                    if(displacement < baseRadius){
                        drawJoystick(event.x, event.y)

                        rudderVM.aileron = (event.x - centerX)/baseRadius
                        rudderVM.elevator = (event.y - centerY)/baseRadius
                    }
                    else {
                        val ratio = baseRadius / displacement
                        val constrainedX = centerX + (event.x - centerX)*ratio
                        val constrainedY = centerY + (event.y - centerY)*ratio

                        drawJoystick(constrainedX.toFloat(), constrainedY.toFloat())

                        rudderVM.aileron = ((constrainedX - centerX)/baseRadius).toFloat()
                        rudderVM.elevator = ((constrainedY - centerY)/baseRadius).toFloat()
                    }
                }
                else {
                    drawJoystick(centerX, centerY)

                    rudderVM.aileron = 0F
                    rudderVM.elevator = 0F
                }
            }
        }
        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder){
        setupDimensions()
        drawJoystick(centerX, centerY)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int){

    }

    override fun surfaceDestroyed(holder: SurfaceHolder){

    }

}