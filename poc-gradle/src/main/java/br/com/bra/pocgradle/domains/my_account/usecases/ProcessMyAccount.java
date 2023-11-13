package br.com.bra.pocgradle.domains.my_account.usecases;

import br.com.bra.pocgradle.domains.my_account.inputs.MyAccountInput;

public interface ProcessMyAccount {
    void execute(MyAccountInput input);
}
