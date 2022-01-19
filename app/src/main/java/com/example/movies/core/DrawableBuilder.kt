package com.example.movies.core

import android.graphics.drawable.GradientDrawable

object DrawableBuilder {
    fun setGradientDrawable(
        colorBase: Int,
        iCornerRadius: Float = 0f,
        iWidthRadius: Int = 0,
        strokeColor: Int = 0): GradientDrawable
    {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(colorBase)
            if (iCornerRadius != 0f)
                cornerRadius = iCornerRadius
            if (iWidthRadius != 0 && strokeColor != 0)
                setStroke(iWidthRadius, strokeColor)
        }
    }
}