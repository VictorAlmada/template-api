package br.com.victoralmada.template_api.dto.request;

import br.com.victoralmada.template_api.entity.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PessoaRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 120, message = "O nome deve conter entre 3 e 120 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "O e-mail deve conter no máximo 150 caracteres.")
    private String email;

    @Size(max = 20, message = "O telefone deve conter no máximo 20 caracteres.")
    private String telefone;

    @Size(max = 255, message = "O telefone deve conter no máximo 255 caracteres.")
    private String endereco;

    @NotNull(message = "O sexo é obrigatório.")
    private Sexo sexo;

}
