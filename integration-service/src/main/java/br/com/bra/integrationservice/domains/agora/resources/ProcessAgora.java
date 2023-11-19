package br.com.bra.integrationservice.domains.agora.resources;

import br.com.bra.integrationservice.domains.agora.inputs.AgoraInput;

public interface ProcessAgora {
    void execute(AgoraInput input);
}
