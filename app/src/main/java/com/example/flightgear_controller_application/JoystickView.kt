package com.example.flightgear_controller_application

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class JoystickView : SurfaceView, SurfaceHolder.Callback{

    constructor(ctx: Context) : super(ctx){
        holder.addCallback(this)
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs){
        holder.addCallback(this)
    }

    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style){
        holder.addCallback(this)
    }

    private fun drawJoystick(newX: Float, newY: Float){
        if(holder.surface.isValid()){
            var canvas = this.holder.lockCanvas()
            var colors = Paint()
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder){

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int){

    }

    override fun surfaceDestroyed(holder: SurfaceHolder){

    }

}