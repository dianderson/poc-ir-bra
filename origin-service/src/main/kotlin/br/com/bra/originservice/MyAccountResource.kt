package br.com.bra.originservice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/my-account")
class MyAccountResource {
    @GetMapping("/{cpf}/{year}")
    fun findByCpfAndYear(
        @PathVariable cpf: String,
        @PathVariable year: Int
    ): ResponseEntity<Response> =
        takeIf { cpf.contains("6") }
            ?.let { ResponseEntity.status(HttpStatus.OK).body(Response(cpf, year)) }
            ?: ResponseEntity(HttpStatus.NO_CONTENT)
}