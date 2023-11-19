package br.com.bra.integrationservice.clients.my_account;

import br.com.bra.integrationservice.domains.my_account.MyAccountInput;
import br.com.bra.integrationservice.domains.my_account.MyAccountModel;
import br.com.bra.integrationservice.domains.my_account.MyAccountClientPort;
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
