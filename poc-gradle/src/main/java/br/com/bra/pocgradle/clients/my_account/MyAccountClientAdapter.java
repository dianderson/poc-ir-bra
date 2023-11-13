package br.com.bra.pocgradle.clients.my_account;

import br.com.bra.pocgradle.domains.my_account.inputs.MyAccountInput;
import br.com.bra.pocgradle.domains.my_account.models.MyAccountModel;
import br.com.bra.pocgradle.domains.my_account.ports.MyAccountClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyAccountClientAdapter implements MyAccountClientPort {
    private final MyAccountClient client;

    @Override
    public MyAccountModel getMyAccountData(MyAccountInput input) {
        return client.findByCpfAndYear(input.getCpf(), input.getYear()).toModel();
    }
}
