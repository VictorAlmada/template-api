package br.com.victoralmada.template_api.service.impl;

import br.com.victoralmada.template_api.dto.request.PessoaRequest;
import br.com.victoralmada.template_api.dto.response.PessoaResponse;
import br.com.victoralmada.template_api.entity.Pessoa;
import br.com.victoralmada.template_api.exception.BusinessException;
import br.com.victoralmada.template_api.exception.ResourceNotFoundException;
import br.com.victoralmada.template_api.mapper.PessoaMapper;
import br.com.victoralmada.template_api.repository.PessoaRepository;
import br.com.victoralmada.template_api.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    @Override
    public List<PessoaResponse> listarTodos() {
        return pessoaRepository.findAll()
                .stream()
                .map(pessoaMapper::toResponse).toList();
    }

    @Override
    public PessoaResponse buscarPorId(UUID id) {
        Pessoa pessoa = buscarPessoa(id);
        return pessoaMapper.toResponse(pessoa);
    }

    @Override
    public PessoaResponse salvar(PessoaRequest request) {

        if (pessoaRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Já existe uma pessoa cadastrada com este e-mail.");
        }

        Pessoa pessoa = pessoaMapper.toEntity(request);
        pessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.toResponse(pessoa);
    }

    public PessoaResponse atualizar(UUID id, PessoaRequest request) {

        Pessoa pessoa = buscarPessoa(id);

        if (!pessoa.getEmail().equals(request.getEmail()) && pessoaRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Já existe uma pessoa cadastrada com este e-mail.");
        }

        pessoaMapper.updateEntity(request, pessoa);
        pessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.toResponse(pessoa);
    }

    @Override
    public void excluir(UUID id) {
        Pessoa pessoa = buscarPessoa(id);
        pessoaRepository.delete(pessoa);
    }

    private Pessoa buscarPessoa(UUID id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Pessoa não encontrada: " + id
        ));
    }
}
