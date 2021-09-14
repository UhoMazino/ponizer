package ru.mazino.ponizer.api.hardware

import ru.mazino.ponizer.api.protocol.Session

interface Device {
    val session: Session
    val ports: Map<Int, List<ONU>>
    val ip: String
    val name: String
    val serial: String
    val community: String
    val softwareVersion: String
    val hardwareVersion: String
    val MAC: String
}
