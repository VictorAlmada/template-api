package br.com.victoralmada.template_api.service;

import br.com.victoralmada.template_api.dto.request.PessoaRequest;
import br.com.victoralmada.template_api.dto.response.PessoaResponse;

import java.util.List;
import java.util.UUID;

public interface PessoaService {

    List<PessoaResponse> listarTodos();

    PessoaResponse buscarPorId(UUID id);

    PessoaResponse salvar(PessoaRequest request);

    PessoaResponse atualizar(UUID id, PessoaRequest request);

    void excluir(UUID id);

}
