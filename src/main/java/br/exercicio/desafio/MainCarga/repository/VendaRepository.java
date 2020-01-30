package br.exercicio.desafio.MainCarga.repository;

import br.exercicio.desafio.MainCarga.entity.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendaRepository extends JpaRepository<VendaEntity, Long> {

    @Query("select saleID from VendaEntity where valorTotal = ( select max(valorTotal) from VendaEntity )")
    public List<Long> findVendaMaisCara();

    @Query("select sum(valorTotal) from VendaEntity where idVendedor = :idVendedor ")
    public Double somaPorVendedor(Long idVendedor);


}
