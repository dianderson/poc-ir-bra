package br.com.bra.pocgradle.domains.my_account;

import br.com.bra.pocgradle.domains.my_account.MyAccountInput;
import br.com.bra.pocgradle.domains.my_account.MyAccountModel;

public interface MyAccountClientPort {
    MyAccountModel getMyAccountData(MyAccountInput input);
}
