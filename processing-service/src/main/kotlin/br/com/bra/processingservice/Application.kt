package br.com.bra.processingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
class processingserviceApplication

fun main(args: Array<String>) {
    runApplication<processingserviceApplication>(*args)
}
