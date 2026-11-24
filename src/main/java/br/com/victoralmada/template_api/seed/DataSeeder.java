package br.com.victoralmada.template_api.seed;

import br.com.victoralmada.template_api.entity.Pessoa;
import br.com.victoralmada.template_api.entity.Sexo;
import br.com.victoralmada.template_api.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PessoaRepository pessoaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (pessoaRepository.count() > 0) {
            return;
        }

        pessoaRepository.save(
                Pessoa.builder()
                        .nome("Victor Almada")
                        .email("victor@email.com")
                        .telefone("(51)99999-9999")
                        .endereco("Gravataí - RS")
                        .sexo(Sexo.MASCULINO)
                        .build()
        );

        pessoaRepository.save(
                Pessoa.builder()
                        .nome("Maria Silva")
                        .email("maria@email.com")
                        .telefone("(51)98888-8888")
                        .endereco("São Paulo - SP")
                        .sexo(Sexo.FEMININO)
                        .build()
        );

    }
}
