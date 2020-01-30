package br.exercicio.desafio.MainCarga.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemDeVendaEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long itemID;

    private Long saleID;

    private Long quantity;

    private Double price;

    private Double valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public Long getSaleID() {
        return saleID;
    }

    public void setSaleID(Long saleID) {
        this.saleID = saleID;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "ItemDeVendaEntity{" +
                "id=" + id +
                ", itemID=" + itemID +
                ", saleID=" + saleID +
                ", quantity=" + quantity +
                ", price=" + price +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
