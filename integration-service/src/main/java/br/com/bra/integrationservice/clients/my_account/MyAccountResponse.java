package br.com.bra.integrationservice.clients.my_account;

import br.com.bra.integrationservice.domains.my_account.MyAccountModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyAccountResponse {
    private String cpf;
    private String name;
    private BigDecimal amount;

    public MyAccountModel toModel() {
        MyAccountModel model = new MyAccountModel();
        model.setCpf(cpf);
        model.setName(name);
        model.setAmount(amount);
        return model;
    }
}
