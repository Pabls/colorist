package ru.ar4i.colorist.domain.colors


import ru.ar4i.colorist.data.entities.Color
import ru.ar4i.colorist.data.repositories.colors.IColorsRepository

class ColorsInteractor(private val colorsRepository: IColorsRepository) :
    IColorsInteractor {
    override suspend fun getColors(): List<Color> {
        return colorsRepository.getColors()
    }
}