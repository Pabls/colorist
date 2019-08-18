package ru.ar4i.colorist.data.repositories.state

interface IStateRepository {
    fun saveLastPosition(position: Int)
    fun getLastPosition(): Int
    fun saveLastSeekbarsState(colors: Triple<Int, Int, Int>)
    fun getLastSeekbarsState(): Triple<Int, Int, Int>
}