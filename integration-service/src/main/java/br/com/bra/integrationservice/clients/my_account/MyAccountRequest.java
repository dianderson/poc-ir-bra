package br.com.bra.integrationservice.clients.my_account;

import lombok.Data;

@Data
public class MyAccountRequest {
    private String cpf;
    private Number year;
}