package br.com.bra.pocgradle.clients.my_account;

import br.com.bra.pocgradle.domains.my_account.models.MyAccountModel;
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
