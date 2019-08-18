package ru.ar4i.colorist.presentation.color_matching.view

import ru.ar4i.colorist.presentation.base.view.IMvpView

interface IColorMatchingView : IMvpView {
    fun setColorName(name: String)
    fun setHexValue(hex: String)
    fun setRgbValue(rgb: String)
}