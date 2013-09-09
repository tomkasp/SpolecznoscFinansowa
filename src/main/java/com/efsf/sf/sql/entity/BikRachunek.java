package com.efsf.sf.sql.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bik_rachunek")
public class BikRachunek implements Serializable{
    
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_rachunek", unique=true, nullable=false)
    private Integer idRachunek;
    
    @Column(length = 255)
    private String relacjaKlienta;
    
    @Column(length = 255)
    private String dataPowstaniaRelacji;
    
    @Column(length = 255)
    private String typTransakcji;
    
    @Column(length = 255)
    private String kwotaZKosztemOds1;
    
    @Column(length = 255)
    private String waluta;
    
    @Column(length = 255)
    private String kwotaZKosztemOds2;  
    
    @Column(length = 255)
    private String bank;
    
    @ManyToOne
    @JoinColumn(name="bik_id")
    private Bik bik;
    
    @OneToMany(mappedBy="rachunek", fetch = FetchType.EAGER)
    private Set<BikHistoriaRachunku> historia;
    
    public Integer getIdRachunek() {
        return idRachunek;
    }

    public void setIdRachunek(Integer idRachunek) {
        this.idRachunek = idRachunek;
    }

    public String getRelacjaKlienta() {
        return relacjaKlienta;
    }

    public void setRelacjaKlienta(String relacjaKlienta) {
        this.relacjaKlienta = relacjaKlienta;
    }

    public String getDataPowstaniaRelacji() {
        return dataPowstaniaRelacji;
    }

    public void setDataPowstaniaRelacji(String dataPowstaniaRelacji) {
        this.dataPowstaniaRelacji = dataPowstaniaRelacji;
    }

    public String getTypTransakcji() {
        return typTransakcji;
    }

    public void setTypTransakcji(String typTransakcji) {
        this.typTransakcji = typTransakcji;
    }

    public String getKwotaZKosztemOds1() {
        return kwotaZKosztemOds1;
    }

    public void setKwotaZKosztemOds1(String kwotaZKosztemOds1) {
        this.kwotaZKosztemOds1 = kwotaZKosztemOds1;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public String getKwotaZKosztemOds2() {
        return kwotaZKosztemOds2;
    }

    public void setKwotaZKosztemOds2(String kwotaZKosztemOds2) {
        this.kwotaZKosztemOds2 = kwotaZKosztemOds2;
    }

    public Bik getBik() {
        return bik;
    }

    public void setBik(Bik bik) {
        this.bik = bik;
    }

    public Set<BikHistoriaRachunku> getHistoria() {
        return historia;
    }

    public void setHistoria(Set<BikHistoriaRachunku> historia) {
        this.historia = historia;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

}
