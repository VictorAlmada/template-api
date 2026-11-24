package br.com.victoralmada.template_api.dto.response;

import br.com.victoralmada.template_api.entity.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private Sexo sexo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

}
