package ru.ar4i.colorist.presentation.color_matching.presenter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ar4i.colorist.R
import ru.ar4i.colorist.data.entities.Color
import ru.ar4i.colorist.domain.colors.IColorsInteractor
import ru.ar4i.colorist.domain.resourses.IResoursesInteractor
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.color_matching.view.IColorMatchingView

class ColorMatchingPresenter(
    private val colorsInteractor: IColorsInteractor,
    private val resoursesInteractor: IResoursesInteractor
) : BasePresenter<IColorMatchingView>() {

    private var colors: List<Color> = listOf()

    override fun attachView(view: IColorMatchingView?) {
        super.attachView(view)
        getColors()
    }

    fun searchColorByHexValue(hex: String, rgb: Triple<Int, Int, Int>) {
        val color = colors.find { it.hex == hex }
        if (color != null) {
            getView()?.setColorName(color.name)
        } else {
            getView()?.setColorName(resoursesInteractor.getStringById(R.string.common_empty))
        }
        getView()?.setHexValue(hex)
        getView()?.setRgbValue("${rgb.first} ${rgb.second} ${rgb.third}")
    }

    private fun getColors() = GlobalScope.launch(Dispatchers.Main) {
        colors = withContext(Dispatchers.IO) { colorsInteractor.getColors() }
    }
}