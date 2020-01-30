package br.exercicio.desafio.MainCarga.repository;

import br.exercicio.desafio.MainCarga.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {


}
