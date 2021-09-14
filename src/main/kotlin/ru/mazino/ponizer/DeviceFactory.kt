package ru.mazino.ponizer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import ru.mazino.ponizer.common.hardware.OLT


class DeviceFactory(val ctx: ApplicationContext) {
    fun createInstance(config: DeviceDTO): OLT {
        with(config) {
            return ctx.getBean(
                OLT::class.java, ip, community, name, serial, softwareVersion, hardwareVesion, MAC
            )
        }
    }
}