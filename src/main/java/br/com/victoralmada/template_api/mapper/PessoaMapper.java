package br.com.victoralmada.template_api.mapper;

import br.com.victoralmada.template_api.dto.request.PessoaRequest;
import br.com.victoralmada.template_api.dto.response.PessoaResponse;
import br.com.victoralmada.template_api.entity.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PessoaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Pessoa toEntity(PessoaRequest pessoaRequest);

    PessoaResponse toResponse(Pessoa pessoa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    void updateEntity(PessoaRequest request, @MappingTarget Pessoa entity);


}
