package ru.ar4i.colorist.data.repositories.state

interface IStateRepository {
    fun saveLastPosition(position: Int)
    fun getLastPosition(): Int
}