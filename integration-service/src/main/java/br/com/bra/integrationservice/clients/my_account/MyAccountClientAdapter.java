package br.com.bra.integrationservice.clients.my_account;

import br.com.bra.integrationservice.domains.my_account.inputs.MyAccountInput;
import br.com.bra.integrationservice.domains.my_account.models.MyAccountDataModel;
import br.com.bra.integrationservice.domains.my_account.ports.MyAccountClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyAccountClientAdapter implements MyAccountClientPort {
    private final MyAccountClient client;

    @Override
    public Optional<MyAccountDataModel> getMyAccountData(MyAccountInput input) {
        return client.findByCpfAndYear(input.getCpf(), input.getYear())
                .map(MyAccountResponse::toModel);
    }
}
