package br.com.bra.integrationservice.clients.agora;

import br.com.bra.integrationservice.domains.agora.models.AgoraDataModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AgoraResponse {
    private String cpf;
    private String name;
    private Integer baseYear;
    private BigDecimal baseAmount;
    private Integer currentYear;
    private BigDecimal currentAmount;

    public AgoraDataModel toModel() {
        return AgoraDataModel.builder()
                .name(name)
                .baseYear(baseYear)
                .baseAmount(baseAmount)
                .currentYear(currentYear)
                .currentAmount(currentAmount)
                .build();
    }
}
