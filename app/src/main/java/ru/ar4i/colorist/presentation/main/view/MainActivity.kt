package ru.ar4i.colorist.presentation.main.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import ru.ar4i.colorist.R
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.base.view.BaseActivity
import ru.ar4i.colorist.presentation.base.view.BaseFragment
import ru.ar4i.colorist.presentation.base.view.IMvpView
import ru.ar4i.colorist.presentation.camera.view.CameraFragment
import ru.ar4i.colorist.presentation.color_matching.view.ColorMatchingFragment
import ru.ar4i.colorist.presentation.colors.adapter.ColorsAdapter
import ru.ar4i.colorist.presentation.colors.view.ColorsFragment
import ru.ar4i.colorist.presentation.main.presenter.MainPresenter

class MainActivity : BaseActivity(), IMainView {

    companion object {
        private val EXTRA_TITLE = "EXTRA_TITLE"
    }

    private val presenter: MainPresenter by inject()

    private lateinit var vBottomNavigation: BottomNavigationView
    private lateinit var vLoader: View
    private lateinit var flContainer: FrameLayout
    private val adapter: ColorsAdapter = ColorsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vLoader = findViewById(R.id.v_progress)
        flContainer = findViewById(R.id.fl_container)
        initBottomNavigationView()
        if (savedInstanceState == null) {
            showFragment(ColorsFragment.newInstance(), getString(R.string.nav_menu_color_lens))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(EXTRA_TITLE, getToolbarTitle())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        var title = savedInstanceState?.getString(EXTRA_TITLE)
        if (title != null)
            setToolbarTitle(title)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getPresenter(): BasePresenter<IMvpView>? {
        return if (presenter is BasePresenter<*>) {
            presenter as BasePresenter<IMvpView>
        } else {
            null
        }
    }

    override fun showLoading() {
        vLoader.visibility = View.VISIBLE
        flContainer.visibility = View.GONE
    }

    override fun hideLoading() {
        vLoader.visibility = View.GONE
        flContainer.visibility = View.VISIBLE
    }

    override fun showColorsFragment(lastPosition: Int) {
        showFragment(ColorsFragment.newInstance(lastPosition), getString(R.string.nav_menu_color_lens))
    }

    override fun showCameraFragment() {
        showFragment(CameraFragment.newInstance(), getString(R.string.nav_menu_camera))
    }

    override fun showColorMatchingFragment() {
        showFragment(ColorMatchingFragment.newInstance(), getString(R.string.nav_menu_color_lens))
    }

    override fun getColorPosition(): Int? {
        val fragment = getFragment()
        var position: Int? = if (checkColorsFragment(fragment)) {
            (fragment as ColorsFragment).getPosition()
        } else {
            null
        }
        return position
    }

    private fun checkColorsFragment(fragment: Fragment?): Boolean = fragment != null && fragment is ColorsFragment

    private fun getFragment(): Fragment? = supportFragmentManager.fragments.first()

    private fun initBottomNavigationView() {
        vBottomNavigation = findViewById(R.id.nav_menu)
        vBottomNavigation.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.action_color_lens -> presenter.colorsMenuItemClicked()
                R.id.action_camera -> presenter.cameraMenuItemClicked()
                R.id.action_colorize -> presenter.colorMatchingMenuItemClicked()
            }
            true
        }
    }

    private fun showFragment(fragment: BaseFragment?, title: String) {
        setToolbarTitle(title)
        setFragment(fragment)
    }

    private fun setFragment(fragment: BaseFragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()
        }
    }
}
