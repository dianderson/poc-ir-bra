package br.com.bra.integrationservice.clients.my_account;

import br.com.bra.integrationservice.domains.my_account.inputs.MyAccountInput;
import br.com.bra.integrationservice.domains.my_account.models.MyAccountDataModel;
import br.com.bra.integrationservice.domains.my_account.ports.MyAccountClientPort;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyAccountClientAdapter implements MyAccountClientPort {
    private static final Logger logger = LogManager.getLogger(MyAccountClientAdapter.class);
    private final MyAccountClient client;

    @Override
    public Optional<MyAccountDataModel> getMyAccountData(MyAccountInput input) {
        logger.info("Consult externally");
        return client.findByCpfAndYear(input.getCpf(), input.getYear())
                .map(MyAccountResponse::toModel);
    }
}
