package br.com.bra.pdfgeneratorservice.domains.agora.usecases;

import br.com.bra.pdfgeneratorservice.domains.agora.inputs.GenerateAgoraPDFInput;
import br.com.bra.pdfgeneratorservice.domains.agora.ports.AgoraKafkaPort;
import br.com.bra.pdfgeneratorservice.domains.agora.resources.GenerateAgoraPDF;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateAgoraPDFImpl implements GenerateAgoraPDF {
    private final AgoraKafkaPort port;

    @Override
    public void execute(GenerateAgoraPDFInput input) {
        port.sendAgoraPDF(input.toModel());
    }
}
