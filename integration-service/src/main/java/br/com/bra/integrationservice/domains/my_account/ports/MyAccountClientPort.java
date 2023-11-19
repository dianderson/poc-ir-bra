package br.com.bra.integrationservice.domains.my_account.ports;

import br.com.bra.integrationservice.domains.my_account.inputs.MyAccountInput;
import br.com.bra.integrationservice.domains.my_account.models.MyAccountDataModel;

import java.util.Optional;

public interface MyAccountClientPort {
    Optional<MyAccountDataModel> getMyAccountData(MyAccountInput input);
}
