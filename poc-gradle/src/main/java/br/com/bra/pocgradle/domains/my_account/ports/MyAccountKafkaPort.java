package br.com.bra.pocgradle.domains.my_account.ports;

import br.com.bra.pocgradle.domains.my_account.models.MyAccountModel;

public interface MyAccountKafkaPort {
    void sendMyAccountData(MyAccountModel model);
}
