package br.com.bra.integrationservice.domains.my_account.resources;

import br.com.bra.integrationservice.domains.my_account.inputs.MyAccountInput;

public interface ProcessMyAccount {
    void execute(MyAccountInput input);
}
