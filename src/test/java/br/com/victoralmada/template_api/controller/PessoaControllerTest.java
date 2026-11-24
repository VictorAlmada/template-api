package br.com.victoralmada.template_api.controller;

import br.com.victoralmada.template_api.dto.response.PessoaResponse;
import br.com.victoralmada.template_api.entity.Sexo;
import br.com.victoralmada.template_api.exception.ResourceNotFoundException;
import br.com.victoralmada.template_api.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PessoaService pessoaService;

    @Test
    void deveBuscarPessoaPorId() throws Exception {

        UUID id = UUID.randomUUID();

        PessoaResponse response = new PessoaResponse();
        response.setId(id);
        response.setNome("Victor Almada");
        response.setEmail("victor@email.com");
        response.setSexo(Sexo.MASCULINO);

        when(pessoaService.buscarPorId(id)).thenReturn(response);

        mockMvc.perform(get("/pessoas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Victor Almada"))
                .andExpect(jsonPath("$.email").value("victor@email.com"))
                .andExpect(jsonPath("$.sexo").value("MASCULINO"));
    }

    @Test
    void deveRetornar404QuandoPessoaNaoExistir() throws Exception {

        UUID id = UUID.randomUUID();

        when(pessoaService.buscarPorId(id)).thenThrow(new ResourceNotFoundException("Pessoa não encontrada: " + id));

        mockMvc.perform(get("/pessoas/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.message").value("Pessoa não encontrada: " + id));
    }

    @Test
    void deveListarTodasAsPessoas() throws Exception {

        UUID id1 =  UUID.randomUUID();
        UUID id2 =  UUID.randomUUID();

        PessoaResponse pessoa1 = PessoaResponse.builder()
                .id(id1)
                .nome("Victor Almada")
                .email("victor@email.com")
                .telefone("(51)99919-1991")
                .endereco("Rua Maráu, 153")
                .sexo(Sexo.MASCULINO)
                .build();

        PessoaResponse pessoa2 = PessoaResponse.builder()
                .id(id2)
                .nome("Maria Silva")
                .email("maria@email.com")
                .telefone("(51)99911-1231")
                .endereco("Rua Corcovado, 194")
                .sexo(Sexo.FEMININO)
                .build();

        when(pessoaService.listarTodos()).thenReturn(List.of(pessoa1, pessoa2));

        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Victor Almada"))
                .andExpect(jsonPath("$[0].email").value("victor@email.com"))
                .andExpect(jsonPath("$[1].nome").value("Maria Silva"))
                .andExpect(jsonPath("$[1].email").value("maria@email.com"));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremPessoas() throws Exception {

        when(pessoaService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        
    }

}
