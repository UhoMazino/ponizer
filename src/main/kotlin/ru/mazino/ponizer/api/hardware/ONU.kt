package ru.mazino.ponizer.api.hardware

interface ONU {
    val oid: String
    val mac: String
    val port: Int
}