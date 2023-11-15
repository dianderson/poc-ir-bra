package br.com.bra.processservice.database.entities

data class IncomeModel(
    val cpf: String,
    val incomes: List<String>
)
