package br.com.bra.pdfgeneratorservice.domains.agora.resources;

import br.com.bra.pdfgeneratorservice.domains.agora.inputs.GenerateAgoraPDFInput;

public interface GenerateAgoraPDF {
    void execute(GenerateAgoraPDFInput input);
}
