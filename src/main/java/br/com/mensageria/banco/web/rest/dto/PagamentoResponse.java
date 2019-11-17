package br.com.mensageria.banco.web.rest.dto;

import lombok.Data;

@Data
public class PagamentoResponse {

    private String message;

    public PagamentoResponse(String message) {
        this.message = message;
    }
}
