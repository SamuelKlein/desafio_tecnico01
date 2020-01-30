package br.exercicio.desafio.MainCarga.repository;

import br.exercicio.desafio.MainCarga.entity.VendedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendedorRepository extends JpaRepository<VendedorEntity, Long> {


    VendedorEntity findByName(String name);

    @Query( "select v from VendedorEntity v where v.valorTotal = (select min(valorTotal) from VendedorEntity) ")
    List<VendedorEntity> piorVendedor();

}
