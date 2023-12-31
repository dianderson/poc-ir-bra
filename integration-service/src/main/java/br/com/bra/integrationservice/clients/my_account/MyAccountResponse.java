package br.com.bra.integrationservice.clients.my_account;

import br.com.bra.integrationservice.domains.my_account.models.MyAccountDataModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyAccountResponse {
    private String cpf;
    private String name;
    private Integer baseYear;
    private BigDecimal baseAmount;
    private Integer currentYear;
    private BigDecimal currentAmount;

    public MyAccountDataModel toModel() {
        return MyAccountDataModel.builder()
                .name(name)
                .baseYear(baseYear)
                .baseAmount(baseAmount)
                .currentYear(currentYear)
                .currentAmount(currentAmount)
                .build();
    }
}
