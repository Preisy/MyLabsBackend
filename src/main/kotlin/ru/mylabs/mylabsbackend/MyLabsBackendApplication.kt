package ru.mylabs.mylabsbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity


@SpringBootApplication
class MyLabsBackendApplication


fun main(args: Array<String>) {
    runApplication<MyLabsBackendApplication>(*args)
}
