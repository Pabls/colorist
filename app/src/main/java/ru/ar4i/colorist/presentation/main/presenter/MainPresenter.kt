package ru.ar4i.colorist.presentation.main.presenter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ar4i.colorist.domain.state.IStateInteractor
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.main.view.IMainView

class MainPresenter(private val stateInteractor: IStateInteractor) : BasePresenter<IMainView>() {

    fun colorsMenuItemClicked() {
        getPositionAndShowColorsFragment()
    }

    fun cameraMenuItemClicked() {
        savePositionAndShowCameraFragment()
    }

    fun colorMatchingMenuItemClicked() {
        savePositionAndShowColorMatchingFragment()
    }

    private fun getPosition(): Int? {
        return getView()?.getColorPosition()
    }

    private fun getPositionAndShowColorsFragment() = GlobalScope.launch(Dispatchers.Main) {
        val lastPosition = withContext(Dispatchers.IO) { getLasyPosition() }
        getView()?.showColorsFragment(lastPosition)
    }

    private fun savePositionAndShowCameraFragment() = GlobalScope.launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { trySavePosition() }
        getView()?.showCameraFragment()
    }

    private fun savePositionAndShowColorMatchingFragment() = GlobalScope.launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { trySavePosition() }
        getView()?.showColorMatchingFragment()
    }

    private suspend fun getLasyPosition(): Int {
       return stateInteractor.getLastPosition()
    }

    private suspend fun trySavePosition() {
        val position = getPosition()
        if (position != null) {
            stateInteractor.saveLastPosition(position!!)
        }
    }
}