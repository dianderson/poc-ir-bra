package br.com.bra.pdfgeneratorservice.domains.my_account.usecases;

import br.com.bra.pdfgeneratorservice.domains.my_account.inputs.GenerateMyAccountPDFInput;
import br.com.bra.pdfgeneratorservice.domains.my_account.ports.MyAccountKafkaPort;
import br.com.bra.pdfgeneratorservice.domains.my_account.resources.GenerateMyAccountPDF;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateMyAccountPDFImpl implements GenerateMyAccountPDF {
    private final MyAccountKafkaPort port;

    @Override
    public void execute(GenerateMyAccountPDFInput input) {
        port.sendMyAccountPDF(input.toModel());
    }
}
