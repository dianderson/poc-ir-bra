package br.com.bra.integrationservice.domains.my_account;

import br.com.bra.integrationservice.domains.my_account.MyAccountInput;
import br.com.bra.integrationservice.domains.my_account.MyAccountModel;

public interface MyAccountClientPort {
    MyAccountModel getMyAccountData(MyAccountInput input);
}
