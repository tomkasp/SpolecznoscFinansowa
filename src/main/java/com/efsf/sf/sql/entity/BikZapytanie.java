package com.efsf.sf.sql.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bik_zapytanie")
public class BikZapytanie implements Serializable{
 
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_zapytanie", unique=true, nullable=false)
    private Integer idZapytania; 
    
    @Column(length = 255)
    private String powod;
    
    @Column(length = 255)
    private String kwota;
    
    @Column(length = 255)
    private String typ;
    
    @Column(length = 255)
    private String data;
    
    @ManyToOne
    @JoinColumn(name="bik_id")
    private Bik bik;


    public Integer getIdZapytania() {
        return idZapytania;
    }


    public void setIdZapytania(Integer idZapytania) {
        this.idZapytania = idZapytania;
    }


    public String getPowod() {
        return powod;
    }


    public void setPowod(String powod) {
        this.powod = powod;
    }


    public String getKwota() {
        return kwota;
    }


    public void setKwota(String kwota) {
        this.kwota = kwota;
    }


    public String getTyp() {
        return typ;
    }


    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the bik
     */
    public Bik getBik() {
        return bik;
    }

    /**
     * @param bik the bik to set
     */
    public void setBik(Bik bik) {
        this.bik = bik;
    }
    

    
}
