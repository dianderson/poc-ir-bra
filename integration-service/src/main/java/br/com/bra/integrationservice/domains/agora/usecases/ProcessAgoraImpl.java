package br.com.bra.integrationservice.domains.agora.usecases;

import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import br.com.bra.integrationservice.domains.agora.inputs.AgoraInput;
import br.com.bra.integrationservice.domains.agora.models.AgoraDataModel;
import br.com.bra.integrationservice.domains.agora.models.AgoraModel;
import br.com.bra.integrationservice.domains.agora.ports.AgoraClientPort;
import br.com.bra.integrationservice.domains.agora.ports.AgoraKafkaPort;
import br.com.bra.integrationservice.domains.agora.resources.ProcessAgora;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessAgoraImpl implements ProcessAgora {
    private final AgoraClientPort clientPort;
    private final AgoraKafkaPort kafkaPort;

    @Override
    public void execute(AgoraInput input) {
        AgoraModel model = new AgoraModel();
        try {
            Optional<AgoraDataModel> dataModel = clientPort.getAgoraData(input);
            model.setCpf(input.getCpf());
            model.setYear(input.getYear());
            model.setStatus(dataModel.isPresent() ? ProcessingStatusEnum.SUCCESS : ProcessingStatusEnum.NOT_FOUND);
            model.setAgoraDataModel(dataModel.orElse(null));
        } catch (Exception ex) {
            model.setCpf(input.getCpf());
            model.setYear(input.getYear());
            model.setStatus(ProcessingStatusEnum.PROCESSING_ERROR);
            model.setAgoraDataModel(null);
        } finally {
            kafkaPort.sendAgoraData(model);
        }
    }
}
