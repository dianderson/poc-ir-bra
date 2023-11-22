package br.com.bra.pdfgeneratorservice.domains.agora.ports;

import br.com.bra.pdfgeneratorservice.domains.agora.models.AgoraPDFModel;

public interface AgoraKafkaPort {
    void sendAgoraPDF(AgoraPDFModel model);
}
