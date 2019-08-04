package ru.ar4i.colorist.data.repositories

import ru.ar4i.colorist.data.entities.Color

interface IColorsRepository {
    fun getColors(): List<Color>
}