package br.com.bra.integrationservice.domains.my_account.usecases;

import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import br.com.bra.integrationservice.domains.my_account.inputs.MyAccountInput;
import br.com.bra.integrationservice.domains.my_account.models.MyAccountDataModel;
import br.com.bra.integrationservice.domains.my_account.models.MyAccountModel;
import br.com.bra.integrationservice.domains.my_account.ports.MyAccountClientPort;
import br.com.bra.integrationservice.domains.my_account.ports.MyAccountKafkaPort;
import br.com.bra.integrationservice.domains.my_account.resources.ProcessMyAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessMyAccountImpl implements ProcessMyAccount {
    private final MyAccountClientPort clientPort;
    private final MyAccountKafkaPort kafkaPort;

    @Override
    public void execute(MyAccountInput input) {
        MyAccountModel model = new MyAccountModel();
        try {
            Optional<MyAccountDataModel> dataModel = clientPort.getMyAccountData(input);
            model.setCpf(input.getCpf());
            model.setYear(input.getYear());
            model.setStatus(dataModel.isPresent() ? ProcessingStatusEnum.SUCCESS : ProcessingStatusEnum.NOT_FOUND);
            model.setMyAccountDataModel(dataModel.orElse(null));
        } catch (Exception ex) {
            model.setCpf(input.getCpf());
            model.setYear(input.getYear());
            model.setStatus(ProcessingStatusEnum.PROCESSING_ERROR);
            model.setMyAccountDataModel(null);
        } finally {
            kafkaPort.sendMyAccountData(model);
        }
    }
}
