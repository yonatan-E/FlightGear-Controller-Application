package com.example.flightgear_controller_application

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class JoystickView : SurfaceView{

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs){

    }

    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    private fun drawJoystick(newX: Float, newY: Float){

    }

    fun surfaceCreated(holder: SurfaceHolder){

    }

    fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int){

    }

    fun surfaceDestroyed(holder: SurfaceHolder){

    }

}