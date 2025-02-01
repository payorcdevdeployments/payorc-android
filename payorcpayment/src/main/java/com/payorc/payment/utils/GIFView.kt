@file:Suppress("DEPRECATION")

package com.payorc.payment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.Movie
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.SystemClock
import android.view.View

@SuppressLint("ViewConstructor")
class GIFView(context: Context, resId: Int) : View(context) {
    private var drawable: AnimatedImageDrawable? = null
    private var gifMovie: Movie? = null
    private var movieStart: Long = 0

    private var fixedWidth = 64
    private var fixedHeight = 64

    init {
        fixedWidth = (context.resources.displayMetrics.widthPixels * 0.2).toInt()
        fixedHeight = (context.resources.displayMetrics.widthPixels * 0.2).toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Use ImageDecoder for API 28+
            val source = ImageDecoder.createSource(context.resources, resId)
            val decodedDrawable = ImageDecoder.decodeDrawable(source)
            if (decodedDrawable is AnimatedImageDrawable) {
                drawable = decodedDrawable
                drawable?.start()
            }
        } else {
            // Use deprecated Movie class for older versions
            val inputStream = context.resources.openRawResource(resId)
            gifMovie = Movie.decodeStream(inputStream)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = (width - fixedWidth) / 2f
        val centerY = (height - fixedHeight) / 2f

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // API 28+: Use AnimatedImageDrawable
            drawable?.setBounds(
                centerX.toInt(),
                centerY.toInt(),
                (centerX + fixedWidth).toInt(),
                (centerY + fixedHeight).toInt()
            )
            drawable?.draw(canvas)
        } else {
            // Pre-API 28: Use Movie for GIF playback
            val now = SystemClock.uptimeMillis()
            if (movieStart == 0L) movieStart = now

            gifMovie?.let {
                val duration = if (it.duration() == 0) 1000 else it.duration()
                val relTime = ((now - movieStart) % duration).toInt()
                it.setTime(relTime)

                // Scale the GIF to 64x64
                canvas.save()
                canvas.translate(centerX, centerY)
                val scaleX = fixedWidth.toFloat() / it.width()
                val scaleY = fixedHeight.toFloat() / it.height()
                canvas.scale(scaleX, scaleY)
                it.draw(canvas, 0f, 0f)
                canvas.restore()

                invalidate() // Keep redrawing for animation
            }
        }
    }
}

