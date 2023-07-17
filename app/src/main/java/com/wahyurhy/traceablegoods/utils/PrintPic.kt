package com.wahyurhy.traceablegoods.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class PrintPic private constructor() {
    var canvas: Canvas? = null
    var paint: Paint? = null
    var bm: Bitmap? = null
    var width: Int = 0
    var length: Float = 0.0F
    var bitbuf: ByteArray? = null

    init {
        instance = this
    }

    companion object {
        private var instance: PrintPic? = null

        fun getInstance(): PrintPic {
            if (instance == null) {
                instance = PrintPic()
            }
            return instance!!
        }
    }

    fun getLength(): Int {
        return (length + 20).toInt()
    }

    fun init(bitmap: Bitmap?) {
        bitmap?.let {
            initCanvas(it.width)
        }
        if (paint == null) {
            initPaint()
        }
        bitmap?.let {
            drawImage(0F, 0F, it)
        }
    }

    fun initCanvas(w: Int) {
        val h = 10 * w

        bm = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
        canvas = Canvas(bm!!)

        canvas?.drawColor(-1)
        width = w
        bitbuf = ByteArray(width / 8)
    }

    fun initPaint() {
        paint = Paint()
        paint?.isAntiAlias = true
        paint?.color = -16777216
        paint?.style = Paint.Style.STROKE
    }

    fun drawImage(x: Float, y: Float, btm: Bitmap?) {
        btm?.let {
            canvas?.drawBitmap(btm, x, y, null)
            if (length < y + btm.height) {
                length = (y + btm.height)
            }
        }
    }

    fun printDraw(): ByteArray {
        val nbm = Bitmap.createBitmap(bm!!, 0, 0, width, getLength())

        val imgbuf = ByteArray(width / 8 * getLength() + 8)

        imgbuf[0] = 29.toByte()
        imgbuf[1] = 118.toByte()
        imgbuf[2] = 48
        imgbuf[3] = 0
        imgbuf[4] = (width / 8).toByte()
        imgbuf[5] = 0
        imgbuf[6] = (getLength() % 256).toByte()
        imgbuf[7] = (getLength() / 256).toByte()

        var s = 7
        for (i in 0 until getLength()) {
            for (k in 0 until width / 8) {
                val c0 = nbm.getPixel(k * 8 + 0, i)
                val p0 = if (c0 == -1) 0 else 1

                val c1 = nbm.getPixel(k * 8 + 1, i)
                val p1 = if (c1 == -1) 0 else 1

                val c2 = nbm.getPixel(k * 8 + 2, i)
                val p2 = if (c2 == -1) 0 else 1

                val c3 = nbm.getPixel(k * 8 + 3, i)
                val p3 = if (c3 == -1) 0 else 1

                val c4 = nbm.getPixel(k * 8 + 4, i)
                val p4 = if (c4 == -1) 0 else 1

                val c5 = nbm.getPixel(k * 8 + 5, i)
                val p5 = if (c5 == -1) 0 else 1

                val c6 = nbm.getPixel(k * 8 + 6, i)
                val p6 = if (c6 == -1) 0 else 1

                val c7 = nbm.getPixel(k * 8 + 7, i)
                val p7 = if (c7 == -1) 0 else 1

                val value = p0 * 128 + p1 * 64 + p2 * 32 + p3 * 16 + p4 * 8 + p5 * 4 + p6 * 2 + p7
                bitbuf!![k] = value.toByte()
            }

            for (t in 0 until width / 8) {
                s++
                imgbuf[s] = bitbuf!![t]
            }
        }

        bm?.recycle()
        bm = null

        return imgbuf
    }
}
