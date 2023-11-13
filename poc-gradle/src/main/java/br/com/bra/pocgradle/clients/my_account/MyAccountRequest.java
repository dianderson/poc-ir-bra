package br.com.bra.pocgradle.clients.my_account;

import lombok.Data;

@Data
public class MyAccountRequest {
    private String cpf;
    private Number year;
}