package ru.ar4i.colorist.presentation.color_matching.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import org.koin.android.ext.android.inject

import ru.ar4i.colorist.R
import ru.ar4i.colorist.presentation.base.presenter.BasePresenter
import ru.ar4i.colorist.presentation.base.view.BaseFragment
import ru.ar4i.colorist.presentation.base.view.IMvpView
import ru.ar4i.colorist.presentation.color_matching.presenter.ColorMatchingPresenter


class ColorMatchingFragment : BaseFragment(), IColorMatchingView {

    companion object {

        private val ONE_PERCENT = 2.55f
        private val DEFAULT_RED_COLOR_VALUE = 255
        private val DEFAULT_GREEN_COLOR_VALUE = 255
        private val DEFAULT_BLUE_COLOR_VALUE = 255
        private val EXTRA_SEEK_BAR_STATE = "EXTRA_SEEK_BAR_STATE"

        @JvmStatic
        fun newInstance(lastSeekbarsState: Triple<Int, Int, Int>): ColorMatchingFragment {
            return ColorMatchingFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_SEEK_BAR_STATE, lastSeekbarsState)
                }
            }
        }
    }

    private val presenter: ColorMatchingPresenter by inject()

    private lateinit var vSelectedColor: View
    private lateinit var sbRed: SeekBar
    private lateinit var sbGreen: SeekBar
    private lateinit var sbBlue: SeekBar
    private lateinit var tvColorName: TextView
    private lateinit var tvHexValue: TextView
    private lateinit var tvRgbValue: TextView

    private var seekBarState: Triple<Int, Int, Int>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            seekBarState = arguments?.getSerializable(EXTRA_SEEK_BAR_STATE) as Triple<Int, Int, Int>?
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        initView(view)
    }

    override fun onResume() {
        super.onResume()
        onStopTrackingTouch()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_color_matching
    }

    override fun getPresenter(): BasePresenter<IMvpView>? {
        return if (presenter is BasePresenter<*>) {
            presenter as BasePresenter<IMvpView>
        } else {
            null
        }
    }

    override fun setColorName(name: String) {
        tvColorName?.text = name
    }

    override fun setHexValue(hex: String) {
        tvHexValue?.text = hex
    }

    override fun setRgbValue(rgb: String) {
        tvRgbValue?.text = rgb
    }

    fun getSeekbarsValue(): Triple<Int, Int, Int> {
        return Triple(
            if (sbRed != null) convertSeekBarValueToColor(sbRed.progress) else DEFAULT_RED_COLOR_VALUE,
            if (sbGreen != null) convertSeekBarValueToColor(sbGreen.progress) else DEFAULT_GREEN_COLOR_VALUE,
            if (sbBlue != null) convertSeekBarValueToColor(sbBlue.progress) else DEFAULT_BLUE_COLOR_VALUE
        )
    }

    private fun initView(view: View) {
        vSelectedColor = view.findViewById(R.id.v_selected_color)
        sbRed = view.findViewById(R.id.sb_red)
        sbGreen = view.findViewById(R.id.sb_green)
        sbBlue = view.findViewById(R.id.sb_blue)
        tvColorName = view.findViewById(R.id.tv_color_name)
        tvHexValue = view.findViewById(R.id.tv_hex_value)
        tvRgbValue = view.findViewById(R.id.tv_rgb_value)
        setListeners()
        setDefaultColor()
    }

    private fun setDefaultColor() {
        sbRed.progress =
            convertColorToSeekBarValue(if (seekBarState != null) seekBarState!!.first else DEFAULT_RED_COLOR_VALUE)
        sbGreen.progress =
            convertColorToSeekBarValue(if (seekBarState != null) seekBarState!!.second else DEFAULT_GREEN_COLOR_VALUE)
        sbBlue.progress =
            convertColorToSeekBarValue(if (seekBarState != null) seekBarState!!.third else DEFAULT_BLUE_COLOR_VALUE)
        changeSelectedColorView(getColor(sbRed.progress, sbGreen.progress, sbBlue.progress))
    }

    private fun convertColorToSeekBarValue(color: Int): Int {
        val result = (color / ONE_PERCENT).toDouble()
        return result.toInt()
    }

    private fun setListeners() {
        sbRed?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                changeSelectedColorView(getColor(i, sbGreen.progress, sbBlue.progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                onStopTrackingTouch()
            }
        })

        sbGreen?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                changeSelectedColorView(getColor(sbRed.progress, i, sbBlue.progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                onStopTrackingTouch()
            }
        })

        sbBlue?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                changeSelectedColorView(getColor(sbRed.progress, sbGreen.progress, i))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                onStopTrackingTouch()
            }
        })
    }

    private fun onStopTrackingTouch() {
        val convertedValues = getConvertedValues(sbRed.progress, sbGreen.progress, sbBlue.progress)
        val color = convertColorToHex(createColor(convertedValues.first, convertedValues.second, convertedValues.third))
        presenter?.searchColorByHexValue(color, convertedValues)
    }

    private fun getConvertedValues(
        redSeekBarValue: Int,
        greenSeekBarValue: Int,
        blueSeekBarValue: Int
    ): Triple<Int, Int, Int> {
        return Triple(
            convertSeekBarValueToColor(redSeekBarValue),
            convertSeekBarValueToColor(greenSeekBarValue),
            convertSeekBarValueToColor(blueSeekBarValue)
        )
    }

    private fun getColor(redSeekBarValue: Int, greenSeekBarValue: Int, blueSeekBarValue: Int): Int {
        val convertedValues = getConvertedValues(redSeekBarValue, greenSeekBarValue, blueSeekBarValue)
        return createColor(convertedValues.first, convertedValues.second, convertedValues.third)
    }

    private fun convertSeekBarValueToColor(value: Int): Int {
        val result = (ONE_PERCENT * value).toDouble()
        return result.toInt()
    }

    private fun createColor(red: Int, green: Int, blue: Int): Int {
        return Color.rgb(red, green, blue)
    }

    private fun changeSelectedColorView(color: Int) {
        vSelectedColor?.setBackgroundColor(color)
    }

    private fun convertColorToHex(color: Int): String {
        return "#" + String.format("%06X", 0xFFFFFF and color).toLowerCase()
    }
}
