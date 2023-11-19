package br.com.bra.integrationservice.clients.my_account;

import br.com.bra.integrationservice.domains.my_account.models.MyAccountDataModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyAccountResponse {
    private String cpf;
    private String name;
    private BigDecimal amount;

    public MyAccountDataModel toModel() {
        return MyAccountDataModel.builder()
                .name(name)
                .amount(amount)
                .build();
    }
}
