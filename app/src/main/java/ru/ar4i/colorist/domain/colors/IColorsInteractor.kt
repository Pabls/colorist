package ru.ar4i.colorist.domain.colors

import ru.ar4i.colorist.data.entities.Color

interface IColorsInteractor {
    suspend fun getColors(): List<Color>
}