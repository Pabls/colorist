package ru.ar4i.colorist.domain.state

import ru.ar4i.colorist.data.repositories.state.IStateRepository

class StateInteractor(private val stateRepository: IStateRepository) : IStateInteractor {

    override suspend fun saveLastPosition(position: Int) {
        stateRepository.saveLastPosition(position)
    }

    override suspend fun getLastPosition(): Int {
        return stateRepository.getLastPosition()
    }
}