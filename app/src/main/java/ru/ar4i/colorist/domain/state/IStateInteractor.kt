package ru.ar4i.colorist.domain.state

interface IStateInteractor {
    suspend fun saveLastPosition(position: Int)
    suspend fun getLastPosition(): Int
    suspend fun saveLastSeekbarsState(colors: Triple<Int, Int, Int>)
    suspend fun getLastSeekbarsState(): Triple<Int, Int, Int>
}