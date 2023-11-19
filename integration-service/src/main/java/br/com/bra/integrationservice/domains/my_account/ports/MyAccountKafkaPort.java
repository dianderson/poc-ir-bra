package br.com.bra.integrationservice.domains.my_account.ports;

import br.com.bra.integrationservice.domains.my_account.models.MyAccountModel;

public interface MyAccountKafkaPort {
    void sendMyAccountData(MyAccountModel model);
}
