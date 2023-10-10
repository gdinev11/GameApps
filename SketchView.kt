package com.example.sketchapp

// George Dinev
// 10/18/2022
// Android Team Project

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.sketchapp.MainActivity.Companion.path
import com.example.sketchapp.MainActivity.Companion.pen


class SketchView : View{

    var params : ViewGroup.LayoutParams? = null
    
    companion object{
        var pathList = ArrayList<android.graphics.Path>()
        var colorList = ArrayList<Int>()
        var currentPen = Color.BLACK
    }

    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    // initialization of color and styling
    private fun init(){
        pen.isAntiAlias = true
        pen.color = currentPen
        pen.style = Paint.Style.STROKE
        pen.strokeJoin = Paint.Join.ROUND
        pen.strokeWidth = 8f

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    // actions when user begins drawing with selected color
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x,y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x,y)
                pathList.add(path)
                colorList.add(currentPen)
            }
            else -> return false
        }
        postInvalidate()
        return false
    }

    // drawing
    override fun onDraw(canvas: Canvas) {

        for(i in pathList.indices){
            pen.setColor(colorList[i])
            canvas.drawPath(pathList[i], pen)
            invalidate()
        }

    }

}