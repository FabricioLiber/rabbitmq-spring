package br.com.mensageria.banco.web.rest.resource;

import br.com.mensageria.banco.service.PagamentoService;
import br.com.mensageria.banco.web.rest.dto.PagamentoRequest;
import br.com.mensageria.banco.web.rest.dto.PagamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pagamentos")
public class PagamentoResource {

    private final PagamentoService pagamentoService;

    public PagamentoResource(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> cadastrarPagamento (@RequestBody PagamentoRequest pagamentoRequest) {
        pagamentoService.enviarPagamento(pagamentoRequest);
        return ResponseEntity.ok().body(new PagamentoResponse("Pagamento realizado."));
    }

}
