package com.example.lab6;

import java.io.Serializable;

/**
 * Created by Olya on 12.12.2017.
 */

public class Product implements Serializable {

    private Long id;
    private String name;
    private String upc;
    private String izgot;
    private Long cena;
    private Long hranenye;
    private Long kol;

    public Product(long id, String name, String upc, String izgot, long cena, long hranenye, Long kol) {
        this.id = id;
        this.name = name;
        this.upc = upc;
        this.izgot = izgot;
        this.cena = cena;
        this.hranenye = hranenye;
        this.kol = kol;
    }

    @Override
    public String toString() {
        return id + " "+this.getUpc()+" "+this.getName()+" "+this.getIzgot()+" ";
    }


    public String getDetails() {
        return
                "Код: " + upc + "\n" +
                        "Производитель: " + izgot+"\n"+
                " Цена: " + cena + "\n" +
                        " Срок хранения: " + hranenye +" дней \n" +
                        " Количество: "+ kol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (hranenye != null ? !hranenye.equals(product.hranenye) : product.hranenye != null)
            return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (upc != null ? upc.hashCode() : 0);
        result = 31 * result + (izgot != null ? izgot.hashCode() : 0);
        result = 31 * result + (cena != null ? cena.hashCode() : 0);
        result = 31 * result + (hranenye != null ? hranenye.hashCode() : 0);
        result = 31 * result + (kol != null ? kol.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUpc() {
        return upc;
    }

    public String getIzgot() {
        return izgot;
    }

    public long getCena() {
        return cena;
    }

    public long getHranenye() {
        return hranenye;
    }

    public Long getKol() {
        return kol;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public void setIzgot(String izgot) {
        this.izgot = izgot;
    }

    public void setCena(long cena) {
        this.cena = cena;
    }

    public void setHranenye(long hranenye) {
        this.hranenye = hranenye;
    }

    public void setKol(Long kol) {
        this.kol = kol;
    }

}
