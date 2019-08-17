package ru.ar4i.colorist.presentation.colors.view

import ru.ar4i.colorist.data.entities.Color
import ru.ar4i.colorist.presentation.base.view.IMvpView

interface IColorsView: IMvpView {
    fun showColors(colors: List<Color>)
}