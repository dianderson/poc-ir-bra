package br.com.bra.integrationservice.domains.my_account;

import br.com.bra.integrationservice.domains.my_account.MyAccountModel;

public interface MyAccountKafkaPort {
    void sendMyAccountData(MyAccountModel model);
}
