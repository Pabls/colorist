package ru.ar4i.colorist.presentation.base.presenter

import ru.ar4i.colorist.presentation.base.view.IMvpView

open class BasePresenter<V : IMvpView> :
    IPresenter<V> {

    private var view: V? = null

    override fun attachView(view: V?) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getView(): V? {
        return view
    }
}