package br.com.bra.pdfgeneratorservice.domains.my_account.inputs;

import br.com.bra.pdfgeneratorservice.common.ProcessingStatusEnum;
import br.com.bra.pdfgeneratorservice.domains.my_account.models.MyAccountPDFModel;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public class GenerateMyAccountPDFInput {
    private String cpf;
    private Integer year;
    private String name;
    private Integer baseYear;
    private BigDecimal baseAmount;
    private Integer currentYear;
    private BigDecimal currentAmount;

    public MyAccountPDFModel toModel() {
        return MyAccountPDFModel.builder()
                .cpf(cpf)
                .year(year)
                .pdfData(buildPDF())
                .status(ProcessingStatusEnum.PROCESSED)
                .build();
    }

    private String buildPDF() {
        return Map.of(
                "cpf", cpf,
                "year", year,
                "name", name,
                "baseYear", baseYear,
                "baseAmount", baseAmount,
                "currentYear", currentYear,
                "currentAmount", currentAmount
        ).toString();
    }
}
