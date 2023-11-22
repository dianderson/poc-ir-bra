package br.com.bra.pdfgeneratorservice.domains.my_account.resources;

import br.com.bra.pdfgeneratorservice.domains.my_account.inputs.GenerateMyAccountPDFInput;

public interface GenerateMyAccountPDF {
    void execute(GenerateMyAccountPDFInput input);
}
