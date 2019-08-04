package ru.ar4i.colorist.data.entities

data class Color(
    var name: String,
    var hex: String,
    var rgb: RGB,
    var luminance: Double
)