package ru.ar4i.colorist.domain.resourses

import ru.ar4i.colorist.data.repositories.resourses.IResoursesRepository

class ResoursesInteractor(private val resoursesRepository: IResoursesRepository) : IResoursesInteractor {
    override fun getStringById(id: Int): String {
        return resoursesRepository.getStringById(id)
    }
}