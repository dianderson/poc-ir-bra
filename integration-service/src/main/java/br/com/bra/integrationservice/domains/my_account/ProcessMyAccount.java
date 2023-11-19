package br.com.bra.integrationservice.domains.my_account;

import br.com.bra.integrationservice.domains.my_account.MyAccountInput;

public interface ProcessMyAccount {
    void execute(MyAccountInput input);
}
