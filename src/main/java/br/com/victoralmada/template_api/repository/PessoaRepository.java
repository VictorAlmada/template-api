package br.com.victoralmada.template_api.repository;

import br.com.victoralmada.template_api.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
    boolean existsByEmail(String email);
}
