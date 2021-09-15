package ru.mazino.ponizer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext
import org.springframework.context.support.beans
import org.springframework.stereotype.Component
import ru.mazino.ponizer.common.hardware.OLT

@Component
class DeviceFactory(val ctx: ApplicationContext) {
    fun createInstance(config: DeviceDTO): OLT {
        with(config) {
            return ctx.getBean(OLT::class.java, ip, community, name, serial, softwareVersion, hardwareVesion, MAC)
        }
    }
}