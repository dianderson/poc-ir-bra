package br.com.bra.integrationservice.clients.agora;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Component
@FeignClient(
        value = "AgoraClient",
        url = "${clients.agora}"
)
public interface AgoraClient {
    @GetMapping("/{cpf}/{year}")
    Optional<AgoraResponse> findByCpfAndYear(
            @PathVariable("cpf") String cpf,
            @PathVariable("year") Number year
    );
}
