package ru.ar4i.colorist.presentation.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter

abstract class BaseFragment : Fragment(), IMvpView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter()?.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getPresenter()?.detachView()
    }

    override fun showLoading() {
        if(activity is IMvpView) {
            (activity as IMvpView).showLoading()
        }
    }

    override fun hideLoading() {
        if(activity is IMvpView) {
            (activity as IMvpView).hideLoading()
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun getPresenter(): BasePresenter<IMvpView>?
}