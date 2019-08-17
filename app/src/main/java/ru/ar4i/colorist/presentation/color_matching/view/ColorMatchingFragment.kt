package ru.ar4i.colorist.presentation.color_matching.view

import org.koin.android.ext.android.inject

import ru.ar4i.colorist.R
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.base.view.BaseFragment
import ru.ar4i.colorist.presentation.base.view.IMvpView
import ru.ar4i.colorist.presentation.color_matching.presenter.ColorMatchingPresenter


class ColorMatchingFragment : BaseFragment(), IColorMatchingView {

    companion object {
        @JvmStatic
        fun newInstance() = ColorMatchingFragment()
    }

    private val presenter: ColorMatchingPresenter by inject()

    override fun getLayoutId(): Int {
        return R.layout.fragment_color_matching
    }

    override fun getPresenter(): BasePresenter<IMvpView>? {
        return if (presenter is BasePresenter<*>) { presenter as BasePresenter<IMvpView> } else { null }
    }
}
