package br.com.bra.pdfgeneratorservice.domains.my_account.ports;

import br.com.bra.pdfgeneratorservice.domains.my_account.models.MyAccountPDFModel;

public interface MyAccountKafkaPort {
    void sendMyAccountPDF(MyAccountPDFModel model);
}
