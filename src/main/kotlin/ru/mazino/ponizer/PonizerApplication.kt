package ru.mazino.ponizer

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.mazino.ponizer.common.hardware.DeviceManager

@SpringBootApplication
class PonizerApplication

fun main(args: Array<String>) {
    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    val manager = ctx.getBean(DeviceManager::class.java)
    manager.devices[0].session.table(arrayOf(".1.3.6.1.4.1.34592.1.3.4.1.1")).forEach { println(it) }
}
