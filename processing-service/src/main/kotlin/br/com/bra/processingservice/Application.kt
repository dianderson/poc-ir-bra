package br.com.bra.processingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
