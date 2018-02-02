package com.danielbostwick.catfacts.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Movie
import android.net.Uri
import android.os.SystemClock
import android.support.annotation.RawRes
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.io.FileNotFoundException
import java.io.InputStream

class GifImageView @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mInputStream: InputStream? = null
    private var mMovie: Movie? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mStart: Long = 0L

    init {
        if (attrs?.getAttributeName(1).equals("background")) {
            val id = Integer.parseInt(attrs!!.getAttributeValue(1).substring(1))
            setGifImageResource(id)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val now: Long = SystemClock.uptimeMillis()

        if (mStart == 0L) {
            mStart = now
        }

        mMovie?.let { movie ->
            val duration = if (movie.duration() == 0) 1000L else movie.duration().toLong()
            val relTime = ((now - mStart) % duration)

            movie.setTime(relTime.toInt())
            movie.draw(canvas, 0f, 0f)
            invalidate()
        }
    }

    fun setGifImageResource(@RawRes id: Int) {
        mInputStream = resources.openRawResource(id)
        initMovie()
    }

    fun setGifImageUri(uri: Uri) {
        try {
            mInputStream = context.getContentResolver().openInputStream(uri)
            initMovie()
        } catch (e: FileNotFoundException) {
            Log.e("GIfImageView", Log.getStackTraceString(e))
        }
    }

    private fun initMovie() {
        setFocusable(true)

        mMovie = Movie.decodeStream(mInputStream).let {
            mWidth = it.width()
            mHeight = it.height()

            return@let it
        }

        requestLayout()
    }
}
