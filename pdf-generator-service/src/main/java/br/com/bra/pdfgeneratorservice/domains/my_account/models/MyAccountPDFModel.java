package br.com.bra.pdfgeneratorservice.domains.my_account.models;

import br.com.bra.pdfgeneratorservice.common.ProcessingStatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyAccountPDFModel {
    private String cpf;
    private Integer year;
    private String pdfData;
    private ProcessingStatusEnum status;
}
