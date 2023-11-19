package br.com.bra.integrationservice.clients.my_account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Component
@FeignClient(
        value = "MyAccountClient",
        url = "${clients.my-account}"
)
public interface MyAccountClient {
    @GetMapping("/{cpf}/{year}")
    Optional<MyAccountResponse> findByCpfAndYear(
            @PathVariable("cpf") String cpf,
            @PathVariable("year") Number year
    );
}
