package ru.ar4i.colorist.data.repositories

import android.content.Context
import com.google.gson.Gson
import ru.ar4i.colorist.BuildConfig
import ru.ar4i.colorist.data.entities.Color
import com.google.gson.reflect.TypeToken
import ru.ar4i.colorist.data.entities.ColorsResponse


class ColorsRepository(
    private val context: Context,
    private val gson: Gson
) : IColorsRepository {

    companion object {
        val ASSERT_PATH = BuildConfig.ASSERT_PATH
    }

    override fun getColors(): List<Color> {
        var jsonString: String? = null
        try {
            jsonString = context.assets
                .open(ASSERT_PATH)
                .bufferedReader()
                .use {
                    it.readText()
                }
        } finally {
            return convertJsonToColors(jsonString)
        }
    }

    private fun convertJsonToColors(jsonString: String?): List<Color> {
        var colors = listOf<Color>()
        try {
            if (jsonString != null) {
                val responseType = object : TypeToken<ColorsResponse>() {}.type
                val res = gson.fromJson<ColorsResponse>(jsonString, responseType)
                colors = res.colors
            }
        } finally {
            return colors
        }
    }
}