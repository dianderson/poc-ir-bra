package br.com.bra.integrationservice.clients.agora;

import br.com.bra.integrationservice.domains.agora.inputs.AgoraInput;
import br.com.bra.integrationservice.domains.agora.models.AgoraDataModel;
import br.com.bra.integrationservice.domains.agora.ports.AgoraClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AgoraClientAdapter implements AgoraClientPort {
    private final AgoraClient client;

    @Override
    public Optional<AgoraDataModel> getAgoraData(AgoraInput input) {
        return client.findByCpfAndYear(input.getCpf(), input.getYear())
                .map(AgoraResponse::toModel);
    }
}
