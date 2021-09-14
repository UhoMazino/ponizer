package ru.mazino.ponizer.providers

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware


class AppProvider : ApplicationContextAware {
    lateinit var context: ApplicationContext
        private set

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}