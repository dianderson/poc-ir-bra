package br.com.bra.pocgradle.domains.my_account.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyAccountModel {
    private String cpf;
    private String name;
    private BigDecimal amount;
}
