package br.exercicio.desafio.MainCarga.repository;

import br.exercicio.desafio.MainCarga.entity.ItemDeVendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDeVendaRepository extends JpaRepository<ItemDeVendaEntity, Long> {

}
