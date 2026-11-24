package br.com.victoralmada.template_api.service;

import br.com.victoralmada.template_api.dto.request.PessoaRequest;
import br.com.victoralmada.template_api.dto.response.PessoaResponse;
import br.com.victoralmada.template_api.entity.Pessoa;
import br.com.victoralmada.template_api.entity.Sexo;
import br.com.victoralmada.template_api.exception.BusinessException;
import br.com.victoralmada.template_api.exception.ResourceNotFoundException;
import br.com.victoralmada.template_api.mapper.PessoaMapper;
import br.com.victoralmada.template_api.repository.PessoaRepository;
import br.com.victoralmada.template_api.service.impl.PessoaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.io.Resource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceImplTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    @InjectMocks
    private PessoaServiceImpl  pessoaService;

    @Test
    void deveBuscarPessoaPorId() {

        UUID id = UUID.randomUUID();

        Pessoa pessoa = Pessoa.builder()
                .id(id)
                .nome("Victor Almada")
                .email("victor@email.com")
                .telefone("(51) 99999-9999")
                .endereco("Gravataí - RS")
                .sexo(Sexo.MASCULINO)
                .build();

        PessoaResponse response = new PessoaResponse();
        response.setId(id);
        response.setNome("Victor Almada");
        response.setEmail("victor@email.com");

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        when(pessoaMapper.toResponse(pessoa)).thenReturn(response);

        PessoaResponse resultado = pessoaService.buscarPorId(id);

        assertEquals(id, resultado.getId());
        assertEquals("Victor Almada", resultado.getNome());
        assertEquals("victor@email.com", resultado.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoPessoaNaoExistir() {

        UUID id = UUID.randomUUID();

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> pessoaService.buscarPorId(id));

        assertEquals("Pessoa não encontrada: " + id, exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaEstiverCadastrado() {

        PessoaRequest request = PessoaRequest.builder()
                .nome("João da Silva")
                .email("joao@email.com")
                .telefone("(51) 99999-9999")
                .endereco("Gravataí - RS")
                .sexo(Sexo.MASCULINO)
                .build();

        when(pessoaRepository.existsByEmail(request.getEmail())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> pessoaService.salvar(request));

        assertEquals("Já existe uma pessoa cadastrada com este e-mail.", exception.getMessage());
    }

    @Test
    void deveSalvarPessoaComSucesso() {

        PessoaRequest request = PessoaRequest.builder()
                .nome("João da Silva")
                .email("joao@email.com")
                .telefone("(51) 99999-9999")
                .endereco("Gravataí - RS")
                .sexo(Sexo.MASCULINO)
                .build();

        Pessoa pessoa = Pessoa.builder()
                .nome("João da Silva")
                .email("joao@email.com")
                .telefone("(51) 99999-9999")
                .endereco("Gravataí - RS")
                .sexo(Sexo.MASCULINO)
                .build();

        Pessoa pessoaSalva = Pessoa.builder()
                .id(UUID.randomUUID())
                .nome("João da Silva")
                .email("joao@email.com")
                .telefone("(51) 99999-9999")
                .endereco("Gravataí - RS")
                .sexo(Sexo.MASCULINO)
                .build();

        PessoaResponse response = new PessoaResponse();
        response.setId(pessoaSalva.getId());
        response.setNome("João da Silva");
        response.setEmail("joao@email.com");

        when(pessoaRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(pessoaMapper.toEntity(request)).thenReturn(pessoa);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoaSalva);
        when(pessoaMapper.toResponse(pessoaSalva)).thenReturn(response);

        PessoaResponse resultado = pessoaService.salvar(request);

        assertEquals(pessoaSalva.getId(), resultado.getId());
        assertEquals("João da Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());

        verify(pessoaRepository).existsByEmail(request.getEmail());
        verify(pessoaMapper).toEntity(request);
        verify(pessoaRepository).save(pessoa);
        verify(pessoaMapper).toResponse(pessoaSalva);
    }

    @Test
    void deveAtualizarPessoaComSucesso() {

        UUID id = UUID.randomUUID();

        Pessoa pessoa = Pessoa.builder()
                .id(id)
                .nome("Victor")
                .email("victor@email.com")
                .telefone("(51) 99999-9999")
                .endereco("Gravataí - RS")
                .sexo(Sexo.MASCULINO)
                .build();

        PessoaRequest request = PessoaRequest.builder()
                .nome("Victor Almada")
                .email("victor.almada@email.com")
                .telefone("(51) 99999-9999")
                .endereco("Gravataí - RS")
                .sexo(Sexo.MASCULINO)
                .build();

        PessoaResponse response = new PessoaResponse();
        response.setId(id);
        response.setNome("Victor Almada");
        response.setEmail("victor.almada@email.com");

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.existsByEmail(request.getEmail())).thenReturn(false);

        doAnswer(invocation -> {
            Pessoa pessoaAtualizada = invocation.getArgument(1);
            pessoaAtualizada.setNome(request.getNome());
            pessoaAtualizada.setEmail(request.getEmail());
            pessoaAtualizada.setSexo(request.getSexo());
            return null;
        }).when(pessoaMapper).updateEntity(request, pessoa);

        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(pessoaMapper.toResponse(pessoa)).thenReturn(response);

        PessoaResponse resultado = pessoaService.atualizar(id, request);

        assertEquals(id, resultado.getId());
        assertEquals("Victor Almada", resultado.getNome());
        assertEquals("victor.almada@email.com", resultado.getEmail());

        verify(pessoaRepository).findById(id);
        verify(pessoaRepository).existsByEmail(request.getEmail());
        verify(pessoaMapper).updateEntity(request, pessoa);
        verify(pessoaRepository).save(pessoa);
        verify(pessoaMapper).toResponse(pessoa);
    }

    @Test
    void deveExcluirPessoaComSucesso() {

        UUID id = UUID.randomUUID();

        Pessoa pessoa = Pessoa.builder()
                .id(id)
                .nome("Victor")
                .email("victor@email.com")
                .telefone("(51)99176-8065")
                .endereco("Rua Figueira, 176")
                .sexo(Sexo.MASCULINO)
                .build();

        when(pessoaRepository.findById(id))
                .thenReturn(Optional.of(pessoa));

        pessoaService.excluir(id);

        verify(pessoaRepository).findById(id);
        verify(pessoaRepository).delete(pessoa);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPessoaInexistente() {

        UUID id = UUID.randomUUID();

        PessoaRequest request = PessoaRequest.builder()
                .nome("Victor Almada")
                .email("victor@email.com")
                .telefone("(51)99176-8065")
                .endereco("Rua Figueira, 176")
                .sexo(Sexo.MASCULINO)
                .build();

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> pessoaService.atualizar(id, request));

        assertEquals("Pessoa não encontrada: " + id, exception.getMessage());

        verify(pessoaRepository).findById(id);
        verify(pessoaRepository, never()).existsByEmail(request.getEmail());
        verify(pessoaMapper, never()).updateEntity(any(), any());
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoAoExcluirPessoaInexistente() {

        UUID id = UUID.randomUUID();

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> pessoaService.excluir(id));

        assertEquals("Pessoa não encontrada: " + id, exception.getMessage());

        verify(pessoaRepository).findById(id);
        verify(pessoaRepository, never()).delete(any());
    }

}
