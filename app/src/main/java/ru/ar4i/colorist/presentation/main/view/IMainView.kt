package ru.ar4i.colorist.presentation.main.view

import ru.ar4i.colorist.presentation.base.view.IMvpView


interface IMainView : IMvpView {
    fun showColorsFragment(lastPosition: Int)
    fun showCameraFragment()
    fun showColorMatchingFragment(lastSeekbarsState: Triple<Int, Int, Int>)
    fun getColorPosition(): Int?
    fun getLastSeekbarsState(): Triple<Int, Int, Int>?
}