package br.exercicio.desafio.MainCarga.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VendaEntity {

    @Id
    private Long saleID;

    private Long idVendedor;

    private String salesmanName;

    private Double valorTotal;

    public Long getSaleID() {
        return saleID;
    }

    public void setSaleID(Long saleID) {
        this.saleID = saleID;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        return "VendaEntity{" +
                "saleID=" + saleID +
                ", idVendedor=" + idVendedor +
                ", salesmanName='" + salesmanName + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
