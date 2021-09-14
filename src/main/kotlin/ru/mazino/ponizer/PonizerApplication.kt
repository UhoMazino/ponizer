package ru.mazino.ponizer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.mazino.ponizer.common.hardware.DeviceManager
import ru.mazino.ponizer.providers.AppProvider

@SpringBootApplication
class PonizerApplication

fun main(args: Array<String>) {
    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    val manager = ctx.getBean(DeviceManager::class.java).also {
        it.init()
    }
    manager.devices[0].session.walk(".1.3.6.1.4.1.34592.1.3.4.1.1.7").forEach { println(it) }
}
