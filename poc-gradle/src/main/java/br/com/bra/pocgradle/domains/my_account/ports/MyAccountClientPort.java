package br.com.bra.pocgradle.domains.my_account.ports;

import br.com.bra.pocgradle.domains.my_account.inputs.MyAccountInput;
import br.com.bra.pocgradle.domains.my_account.models.MyAccountModel;

public interface MyAccountClientPort {
    MyAccountModel getMyAccountData(MyAccountInput input);
}
