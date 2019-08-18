package ru.ar4i.colorist.presentation.colors.presenter

import android.os.Looper
import kotlinx.coroutines.*
import ru.ar4i.colorist.data.entities.Color
import ru.ar4i.colorist.domain.colors.IColorsInteractor
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.colors.view.IColorsView

class ColorsPresenter(private val colorsInteractor: IColorsInteractor) : BasePresenter<IColorsView>() {

    companion object {
        private val MIN_SEARCH_STRING_LENGTH = 2
        private val DELAY = 500L
    }

    private var colors: List<Color> = listOf()
    private var textChangedJob: Job? = null
    private var currentSearchQuery: String = ""

    override fun attachView(view: IColorsView?) {
        super.attachView(view)
        getColors()
    }

    override fun detachView() {
        textChangedJob = null
        super.detachView()
    }

    fun queryTextChange(query: String?) {
        if (query != null) {
            if (query.length > MIN_SEARCH_STRING_LENGTH && query != currentSearchQuery) {
                currentSearchQuery = query
                textChangedJob?.cancel()
                textChangedJob = searchColorByName(query)
            } else if (query.isEmpty()) {
                setColors(colors)
            }
        }
    }

    private fun getColors() = GlobalScope.launch(Dispatchers.Main) {
        getView()?.showLoading()
        colors = withContext(Dispatchers.IO) { colorsInteractor.getColors() }
        getView()?.hideLoading()
        setColors(colors)
    }

    private fun searchColorByName(name: String) = GlobalScope.launch(Dispatchers.Main) {
        delay(DELAY)
        val filteredColors = withContext(Dispatchers.IO) { searchColors(name) }
        setColors(filteredColors)
    }

    private fun setColors(colors: List<Color>) {
        getView()?.showColors(colors)
    }

    private fun searchColors(name: String): List<Color> {
        return colors.filter { it.name.contains(name, true) }
    }
}