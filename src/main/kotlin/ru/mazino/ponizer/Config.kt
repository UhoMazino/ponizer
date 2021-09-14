package ru.mazino.ponizer

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource


@Configuration
@ComponentScan("ru.mazino.ponizer")
@PropertySource("classpath:app.properties")
class Config