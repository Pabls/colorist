package ru.ar4i.colorist.presentation.base.presenter

import ru.ar4i.colorist.presentation.base.view.IMvpView

interface IPresenter<V : IMvpView> {
    fun attachView(view: V?)
    fun detachView()
    fun getView(): V?
}
