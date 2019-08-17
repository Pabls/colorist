package ru.ar4i.colorist.presentation.camera.view

import org.koin.android.ext.android.inject

import ru.ar4i.colorist.R
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.base.view.BaseFragment
import ru.ar4i.colorist.presentation.base.view.IMvpView
import ru.ar4i.colorist.presentation.camera.presenter.CameraPresenter
import ru.ar4i.colorist.presentation.colors.view.ColorsFragment

class CameraFragment : BaseFragment(), ICameraView {

    companion object {
        @JvmStatic
        fun newInstance() = CameraFragment()
    }

    private val presenter: CameraPresenter by inject()

    override fun getLayoutId(): Int {
        return R.layout.fragment_camera
    }

    override fun getPresenter(): BasePresenter<IMvpView>? {
        return if (presenter is BasePresenter<*>)
            presenter as BasePresenter<IMvpView>
        else null
    }
}
