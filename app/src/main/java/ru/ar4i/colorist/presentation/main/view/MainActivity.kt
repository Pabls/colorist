package ru.ar4i.colorist.presentation.main.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.base.view.BaseActivity
import ru.ar4i.colorist.presentation.base.view.BaseFragment
import ru.ar4i.colorist.presentation.base.view.IMvpView
import ru.ar4i.colorist.presentation.camera.view.CameraFragment
import ru.ar4i.colorist.presentation.color_matching.view.ColorMatchingFragment
import ru.ar4i.colorist.presentation.colors.adapter.ColorsAdapter
import ru.ar4i.colorist.presentation.colors.view.ColorsFragment
import ru.ar4i.colorist.presentation.main.presenter.MainPresenter
import ru.ar4i.colorist.R


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

    override fun showColorMatchingFragment(lastSeekbarsState: Triple<Int, Int, Int>) {
        showFragment(ColorMatchingFragment.newInstance(lastSeekbarsState), getString(R.string.nav_menu_colorize))
    }

    override fun getColorPosition(): Int? {
        val fragment = getFragment()
        return if (checkFragmentType(fragment, ColorsFragment::class.java)) {
            (fragment as ColorsFragment).getPosition()
        } else {
            null
        }
    }

    override fun getLastSeekbarsState(): Triple<Int, Int, Int>? {
        val fragment = getFragment()
        return if (checkFragmentType(fragment, ColorMatchingFragment::class.java)) {
            (fragment as ColorMatchingFragment).getSeekbarsValue()
        } else {
            null
        }
    }

    private fun <T> checkFragmentType(fragment: Fragment?, clazz: Class<T>): Boolean = fragment != null && clazz.isInstance(fragment)
    
    private fun getFragment(): Fragment? {
        return if (!supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.fragments.first()
        } else {
            null
        }
    }

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
        if (fragment != null && !isFragmentEquals(fragment, getFragment())) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()
        }
    }

    private fun isFragmentEquals(fragmentOne: Fragment?, fragmentTwo: Fragment?): Boolean {
        return fragmentOne != null && fragmentTwo != null && fragmentOne.javaClass == fragmentTwo.javaClass
    }
}
