package com.sporting.hokkey

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception
import java.util.*
import kotlin.math.abs

class GameView(ctx: Context, attributeSet: AttributeSet): SurfaceView(ctx,attributeSet) {

    var rocket = BitmapFactory.decodeResource(ctx.resources,R.mipmap.rocket_foreground)
    var fire = BitmapFactory.decodeResource(ctx.resources,R.mipmap.fire_foreground)
    var bg = BitmapFactory.decodeResource(ctx.resources,R.drawable.bg)
    var star = BitmapFactory.decodeResource(ctx.resources,R.mipmap.star_foreground)
    val paintC = Paint().apply {
        color = Color.BLACK
    }

    public var score = 0
    private val random = Random()
    private var y = 0
    private var dy = 0
    private val offset = 20
    private var millis = 0
    private var listener: EndListener? = null
    private var gx = 0f
    private var gy = 0f
    private var my = 0f
    private var mx = 0f
    private var bx = -100f
    private var by = -100f
    private var tmp = 0f
    private var th = false;
    private var paintT: Paint = Paint().apply {
        textSize = 80f
        color = Color.WHITE
    }
    private val player = MediaPlayer.create(ctx,R.raw.bg)
    private val kick = MediaPlayer.create(ctx,R.raw.kick)
    private val sound = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("sounds",true)
    private val music = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("music",true)
    private var paintB: Paint = Paint(Paint.DITHER_FLAG)

    init {
        player.setOnCompletionListener {
            player.start()
        }
        rocket = Bitmap.createScaledBitmap(rocket,rocket.width/2,rocket.height/2,true)
        holder.addCallback(object : SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                val canvas = holder.lockCanvas()
                if(canvas!=null) {
                    y = canvas.width/2
                    gy = -fire.height.toFloat()
                    gx = random.nextInt(300)+canvas.width/2f
                    my = -star.height.toFloat()
                    mx = random.nextInt(300)+canvas.width/2f
                    if(music) player.start()
                    draw(canvas)
                    holder.unlockCanvasAndPost(canvas)
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
               if(music) player.stop()
            }

        })
        val updateThread = Thread {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (!paused) {
                        update.run()
                        millis ++
                    }
                }
            }, 500, 16)
        }

        updateThread.start()
    }
    var code = -1f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action) {
            MotionEvent.ACTION_UP -> {
                code = -1f
            }
            MotionEvent.ACTION_DOWN -> {
                code = event.x
            }
        }
        postInvalidate()
        return true
    }

    var paused = false
    var delta = 8
    val update = Runnable{
        var isEnd = false
        try {
            val canvas = holder.lockCanvas()
            if(gx<0) {
                gx = canvas.width.toFloat()
                gy = random.nextInt(canvas.width).toFloat()
            }
            if(by<-80) th = false
            else {
                by -= 15
            }
            if(code>=0) {
                if(code>y) y+=delta
                else y-=delta
            }
            if(y<=-rocket.width) y = canvas.width
            if(y>=rocket.width+canvas.width) y = 0
            gy+=4
            my+=4
            if(gy>canvas.height) {
                gy = -fire.height.toFloat()
                gx = random.nextInt(canvas.width).toFloat()
            }
            if(my>canvas.height) {
                my = -star.height.toFloat()
                mx = random.nextInt(canvas.width).toFloat()
            }
            if(abs(gx-y)<=rocket.width/2 && gy>=canvas.height-rocket.height) {
                isEnd = true
            }
            if(abs(mx-y)<=rocket.width/2 && abs(my-canvas.height+rocket.height.toFloat())<=rocket.height/2) {
                isEnd = true
            }
            if(bx-mx<=star.width && bx-mx>0 && abs(my-by)<=star.height/2 && th) {
                score++
                my = 0f
                mx = random.nextInt(canvas.width).toFloat()
                bx = -100f
                by = -100f
                th = false
            }
            if(bx-gx<=fire.width  && bx-gx>0 && abs(gy-by)<=fire.height && th) {
                bx = -100f
                by = -100f
                th = false
            }
            tmp = canvas.height-rocket.height.toFloat()-70
            canvas.drawBitmap(bg,0f,0f,paintB)
            if(th) canvas.drawCircle(bx,by,20f,paintC)
            canvas.drawText("$score",50f,100f,paintT)
            canvas.drawBitmap(rocket,y.toFloat(),canvas.height-rocket.height.toFloat()-50,paintB)
            canvas.drawBitmap(fire, gx, gy,paintB)
            canvas.drawBitmap(star, mx, my,paintB)
            holder.unlockCanvasAndPost(canvas)
            // Log.d("TAG","$isEnd")
            if(isEnd) {
                Log.d("TAG","END")
                togglePause()
                if(listener!=null) listener!!.end()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setEndListener(list: EndListener) {
        this.listener = list
    }
    fun togglePause() {
        paused = !paused
    }
    fun throwBall() {
        if(th) return
        if(sound) kick.start()
        th = true
        bx = y.toFloat()
        by = tmp
    }
    companion object {
        interface EndListener {
            fun end();
        }
    }

}