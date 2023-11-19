package br.com.bra.integrationservice.domains.my_account.models;

import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import lombok.Data;

@Data
public class MyAccountModel {
    private String cpf;
    private Integer year;
    private ProcessingStatusEnum status;
    private MyAccountDataModel myAccountDataModel;
}
