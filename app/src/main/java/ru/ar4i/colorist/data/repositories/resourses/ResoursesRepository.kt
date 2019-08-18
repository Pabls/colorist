package ru.ar4i.colorist.data.repositories.resourses

import android.content.Context

class ResoursesRepository(private val context: Context) : IResoursesRepository {

    override fun getStringById(id: Int): String {
        return context.getString(id)
    }
}