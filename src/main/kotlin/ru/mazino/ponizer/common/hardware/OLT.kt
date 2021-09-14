package ru.mazino.ponizer.common.hardware

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.mazino.ponizer.api.hardware.Device
import ru.mazino.ponizer.api.hardware.ONU
import ru.mazino.ponizer.api.protocol.Session
import ru.mazino.ponizer.common.protocol.sessions.SNMPSession

@Component
@Scope("prototype")
class OLT(
    override val ip: String,
    override val community: String,
    override val name: String,
    override val serial: String,
    override val softwareVersion: String,
    override val hardwareVersion: String,
    override val MAC: String,
) : Device {
    override val session: Session
    override lateinit var ports: Map<Int, List<ONU>>

    init {
        session = SNMPSession(ip, community)
    }
}
