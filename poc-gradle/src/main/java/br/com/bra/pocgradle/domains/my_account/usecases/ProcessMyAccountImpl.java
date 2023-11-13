package br.com.bra.pocgradle.domains.my_account.usecases;

import br.com.bra.pocgradle.domains.my_account.inputs.MyAccountInput;
import br.com.bra.pocgradle.domains.my_account.ports.MyAccountClientPort;
import br.com.bra.pocgradle.domains.my_account.ports.MyAccountKafkaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessMyAccountImpl implements ProcessMyAccount {
    private final MyAccountClientPort clientPort;
    private final MyAccountKafkaPort kafkaPort;

    @Override
    public void execute(MyAccountInput input) {
        kafkaPort.sendMyAccountData(
                clientPort.getMyAccountData(input)
        );
    }
}
