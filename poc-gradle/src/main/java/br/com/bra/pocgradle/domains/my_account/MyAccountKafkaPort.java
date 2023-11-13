package br.com.bra.pocgradle.domains.my_account;

import br.com.bra.pocgradle.domains.my_account.MyAccountModel;

public interface MyAccountKafkaPort {
    void sendMyAccountData(MyAccountModel model);
}
