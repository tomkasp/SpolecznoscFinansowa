package com.efsf.sf.sql.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="bik_historia_rachunku")
public class BikHistoriaRachunku implements Serializable{
 
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_wpisu", unique=true, nullable=false)
    private Integer idWpisu;
    
    @Column
    private String numer;
    
    @Column
    private String data;
    
    @Column
    private String status;
    @Column
    private String kwotaDoSplaty;
    
    @Column
    private String saldoNaleznosci;
    
    @Column
    private String opoznienie;
    
    @Column
    private String zaleglePlatnosci;
    
    @Column
    private String saldoLimitu;
    
    @Column
    private String waluta; 
    
    @ManyToOne
    @JoinColumn(name="id_rachunek")
    private BikRachunek rachunek;

    public Integer getIdWpisu() {
        return idWpisu;
    }

    public void setIdWpisu(Integer idWpisu) {
        this.idWpisu = idWpisu;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKwotaDoSplaty() {
        return kwotaDoSplaty;
    }

    public void setKwotaDoSplaty(String kwotaDoSplaty) {
        this.kwotaDoSplaty = kwotaDoSplaty;
    }

    public String getSaldoNaleznosci() {
        return saldoNaleznosci;
    }

    public void setSaldoNaleznosci(String saldoNaleznosci) {
        this.saldoNaleznosci = saldoNaleznosci;
    }
    
    public String getOpoznienie() {
        return opoznienie;
    }

    public void setOpoznienie(String opoznienie) {
        this.opoznienie = opoznienie;
    }

    public String getZaleglePlatnosci() {
        return zaleglePlatnosci;
    }

    public void setZaleglePlatnosci(String zaleglePlatnosci) {
        this.zaleglePlatnosci = zaleglePlatnosci;
    }

    public String getSaldoLimitu() {
        return saldoLimitu;
    }

    public void setSaldoLimitu(String saldoLimitu) {
        this.saldoLimitu = saldoLimitu;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public BikRachunek getRachunek() {
        return rachunek;
    }

    public void setRachunek(BikRachunek rachunek) {
        this.rachunek = rachunek;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
