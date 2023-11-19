package br.com.bra.integrationservice.domains.my_account.inputs;

import lombok.Data;

@Data
public class MyAccountInput {
    private String cpf;
    private Integer year;
}