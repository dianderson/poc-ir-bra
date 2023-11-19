package br.com.bra.integrationservice.domains.agora.ports;

import br.com.bra.integrationservice.domains.agora.inputs.AgoraInput;
import br.com.bra.integrationservice.domains.agora.models.AgoraDataModel;

import java.util.Optional;

public interface AgoraClientPort {
    Optional<AgoraDataModel> getAgoraData(AgoraInput input);
}
