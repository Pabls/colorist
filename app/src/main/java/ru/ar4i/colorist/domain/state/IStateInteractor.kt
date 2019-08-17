package ru.ar4i.colorist.domain.state

interface IStateInteractor {
    suspend fun saveLastPosition(position: Int)
    suspend fun getLastPosition(): Int
}