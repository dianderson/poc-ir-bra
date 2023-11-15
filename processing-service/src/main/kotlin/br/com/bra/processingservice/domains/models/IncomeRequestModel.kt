package br.com.bra.processingservice.domains.models

data class IncomeRequestModel(
    val cpf: String,
    val year: Int,
    val awaitingProcessing: String?
)
