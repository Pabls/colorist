package ru.ar4i.colorist.presentation.colors.presenter

import kotlinx.coroutines.*
import ru.ar4i.colorist.data.entities.Color
import ru.ar4i.colorist.domain.colors.IColorsInteractor
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.colors.view.IColorsView

class ColorsPresenter(private val colorsInteractor: IColorsInteractor) : BasePresenter<IColorsView>() {

    private var colors: List<Color> = listOf()

    override fun attachView(view: IColorsView?) {
        super.attachView(view)
        getColors()
    }

    private fun getColors() = GlobalScope.launch(Dispatchers.Main) {
        getView()?.showLoading()
        colors = withContext(Dispatchers.IO) { colorsInteractor.getColors() }
        getView()?.hideLoading()
        getView()?.showColors(colors)
    }
}