package br.com.bra.integrationservice.domains.my_account.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MyAccountDataModel {
    private String name;
    private Integer baseYear;
    private BigDecimal baseAmount;
    private Integer currentYear;
    private BigDecimal currentAmount;
}
