package br.com.bra.pocgradle.domains.my_account.inputs;

import lombok.Data;

@Data
public class MyAccountInput {
    private String cpf;
    private Number year;
}