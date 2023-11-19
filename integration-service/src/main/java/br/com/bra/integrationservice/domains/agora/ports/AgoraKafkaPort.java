package br.com.bra.integrationservice.domains.agora.ports;

import br.com.bra.integrationservice.domains.agora.models.AgoraModel;

public interface AgoraKafkaPort {
    void sendAgoraData(AgoraModel model);
}
