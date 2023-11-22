package br.com.bra.pdfgeneratorservice.domains.agora.models;

import br.com.bra.pdfgeneratorservice.common.ProcessingStatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgoraPDFModel {
    private String cpf;
    private Integer year;
    private String pdfData;
    private ProcessingStatusEnum status;
}
