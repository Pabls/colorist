package ru.ar4i.colorist.presentation.main.presenter

import kotlinx.coroutines.*
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

    private fun getPositionAndShowColorsFragment() = GlobalScope.launch(Dispatchers.Main) {
        trySaveLastSeekbarsState()
        val lastPosition = withContext(Dispatchers.IO) { stateInteractor.getLastPosition() }
        getView()?.showColorsFragment(lastPosition)
    }

    private fun trySaveLastSeekbarsState() = GlobalScope.launch(Dispatchers.Main){
        val lastSeekbarsState = getView()?.getLastSeekbarsState()
        withContext(Dispatchers.IO) { saveLastSeekbarsState(lastSeekbarsState)}
    }

    private fun savePositionAndShowCameraFragment() = GlobalScope.launch(Dispatchers.Main) {
        trySaveLastSeekbarsState()
        withContext(Dispatchers.IO) { trySavePosition() }
        getView()?.showCameraFragment()
    }

    private fun savePositionAndShowColorMatchingFragment() = GlobalScope.launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) { trySavePosition() }
        val lastSeekbarsState = async(Dispatchers.IO) { getLastSeekbarsState() }
        getView()?.showColorMatchingFragment(lastSeekbarsState.await())
    }

    private suspend fun trySavePosition() {
        val position = getView()?.getColorPosition()
        if (position != null) {
            stateInteractor.saveLastPosition(position!!)
        }
    }

    private suspend fun saveLastSeekbarsState(colors: Triple<Int, Int, Int>?) {
        if (colors != null)
            stateInteractor.saveLastSeekbarsState(colors)
    }

    private suspend fun getLastSeekbarsState(): Triple<Int, Int, Int> {
        return stateInteractor.getLastSeekbarsState()
    }

}