package br.com.bra.integrationservice.domains.agora.inputs;

import lombok.Data;

@Data
public class AgoraInput {
    private String cpf;
    private Integer year;
}