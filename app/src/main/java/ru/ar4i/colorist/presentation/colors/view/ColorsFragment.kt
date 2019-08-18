package ru.ar4i.colorist.presentation.colors.view

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import ru.ar4i.colorist.R
import ru.ar4i.colorist.data.entities.Color
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.base.view.BaseFragment
import ru.ar4i.colorist.presentation.base.view.IMvpView
import ru.ar4i.colorist.presentation.colors.adapter.ColorsAdapter
import ru.ar4i.colorist.presentation.colors.presenter.ColorsPresenter
import java.lang.Exception


class ColorsFragment : BaseFragment(), IColorsView {

    companion object {

        private val EXTRA_LAST_POSITION = "EXTRA_LAST_POSITION"
        private val DEFAULT_POSITION = 0

        @JvmStatic
        fun newInstance(lastPosition: Int = DEFAULT_POSITION): ColorsFragment {
            return ColorsFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_LAST_POSITION, lastPosition)
                }
            }
        }
    }

    private val presenter: ColorsPresenter by inject()

    private lateinit var rvColors: RecyclerView
    private var searchViewEditText: SearchView? = null
    private val adapter: ColorsAdapter = ColorsAdapter()
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(EXTRA_LAST_POSITION, DEFAULT_POSITION)
        initView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.colors_fragment_menu, menu)
        val actionSearch = menu?.findItem(R.id.menu_search)
        searchViewEditText = actionSearch?.actionView as SearchView
        searchViewEditText?.queryHint = getString(R.string.menu_search_hint)
        searchViewEditText?.inputType  = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        searchViewEditText?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.queryTextChange(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.queryTextChange(newText)
                return true
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_colors
    }

    override fun getPresenter(): BasePresenter<IMvpView>? {
        return if (presenter is BasePresenter<*>)
            presenter as BasePresenter<IMvpView>
        else null
    }

    override fun showColors(colors: List<Color>) {
        rvColors?.visibility = View.VISIBLE
        adapter.setData(colors)
        if (position != null && position!! > DEFAULT_POSITION)
            rvColors.scrollToPosition(position!!)
    }

    fun getPosition(): Int {
        return if (rvColors != null) {
            try {
                (rvColors.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            } catch (exception: Exception) {
                DEFAULT_POSITION
            }

        } else {
            DEFAULT_POSITION
        }
    }

    private fun initView(view: View) {
        rvColors = view.findViewById(R.id.rv_colors)
        rvColors.adapter = adapter
    }
}
