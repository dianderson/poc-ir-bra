package br.com.bra.integrationservice.domains.agora.models;

import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import lombok.Data;

@Data
public class AgoraModel {
    private String cpf;
    private Integer year;
    private ProcessingStatusEnum status;
    private AgoraDataModel agoraDataModel;
}
