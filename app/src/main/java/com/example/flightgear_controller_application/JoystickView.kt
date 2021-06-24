package com.example.flightgear_controller_application

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt

class JoystickView : SurfaceView, SurfaceHolder.Callback, View.OnTouchListener{

    lateinit var joystickCallback: JoystickListener
    private var centerX: Float = 0.0f
    private var centerY: Float = 0.0f
    private var baseRadius: Float = 0.0f
    private var hatRadius: Float = 0.0f


    constructor(context: Context) : super(context){
        holder.addCallback(this)
        setOnTouchListener(this)
        if(context is JoystickListener?){
            this.joystickCallback =  context
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        holder.addCallback(this)
        setOnTouchListener(this)
        if(context is JoystickListener?){
            this.joystickCallback =  context
        }
    }

    constructor(context: Context, attrs: AttributeSet, style: Int) : super(context, attrs, style){
        holder.addCallback(this)
        setOnTouchListener(this)
        if(context is JoystickListener?){
            this.joystickCallback =  context
        }
    }

    private fun setupDimentions(){
        this.centerX = (width/2).toFloat()
        this.centerY = (height/2).toFloat()
        this.baseRadius = (Math.min(width, height) / 2.5).toFloat()
        this.hatRadius = (Math.min(width, height) / 5).toFloat()
    }

    private fun drawJoystick(newX: Float, newY: Float){
        if(holder.surface.isValid()){
            var canvas = this.holder.lockCanvas()
            var colors = Paint()
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
                    var displacement = sqrt(
                        (event.x - centerX).toDouble().pow(2.0) + (event.y - centerY).toDouble()
                            .pow(2.0)
                    )
                    if(displacement < baseRadius){
                        drawJoystick(event.x, event.y)
                        joystickCallback.onJoystickMoved((event.x - centerX)/baseRadius, (event.y - centerY)/baseRadius, id)
                    }
                    else{
                        var ratio = baseRadius / displacement
                        var constrainedX = centerX + (event.x - centerX)*ratio
                        var constrainedY = centerY + (event.y - centerY)*ratio
                        drawJoystick(constrainedX.toFloat(), constrainedY.toFloat())
                        joystickCallback.onJoystickMoved(((constrainedX - centerX)/baseRadius).toFloat(),
                            ((constrainedY - centerY)/baseRadius).toFloat(), id)
                    }
                }
                else{
                    drawJoystick(centerX, centerY)
                    joystickCallback.onJoystickMoved(0.0F, 0.0F, id)
                }
            }
        }
        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder){
        setupDimentions()
        drawJoystick(centerX, centerY)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int){

    }

    override fun surfaceDestroyed(holder: SurfaceHolder){

    }

}

interface JoystickListener{
    fun onJoystickMoved(xPercent: Float, yPercent: Float, id: Int)
}