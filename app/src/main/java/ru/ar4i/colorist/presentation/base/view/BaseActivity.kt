package ru.ar4i.colorist.presentation.base.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.ar4i.colorist.R
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter

abstract class BaseActivity : AppCompatActivity(), IMvpView {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        getPresenter()?.attachView(this)
        initToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun getPresenter(): BasePresenter<IMvpView>?

    protected fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    protected fun getToolbarTitle(): String? {
        return supportActionBar?.title.toString()
    }

    protected fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null)
            setSupportActionBar(toolbar)
    }
}