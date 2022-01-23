package com.example.movies.ui.components

import android.graphics.*
import com.squareup.picasso.Transformation

class RoundCorners: Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint =  Paint()
        val rect =  Rect(0, 0, source.width, source.height)
        val rectF = RectF(rect)
        val roundPx = source.width * 0.1f

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(source, rect, rect, paint)
        source.recycle()
        return output
    }

    override fun key(): String = "RoundImage"
}