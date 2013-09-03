package com.efsf.sf.sql.entity;

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
@Table(name="bik")
public class Bik implements java.io.Serializable{
    
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_bik", unique=true, nullable=false)
    private Integer idBik;
    
    @Column(length = 255)
    private String pesel;
    
    @Column(length = 255)
    private String ocenaPunktowa;
    
    @Column(length = 255)
    private String klasa;
    
    @Column(length = 255)
    private String rachunkiKwestionowane;
    
    @Column(length = 255)
    private String zablokowanerekordyBIORK;
    
    @Column(length = 255)
    private String rzZaleglosc030dni;
    
    @Column(length = 255)
    private String rzUmorzone;
    
    @Column(length = 255)
    private String rzOdzyskane;
   
    @Column(length = 255)
    private String roZaleglosc030dni;
    
    @Column(length = 255)
    private String roZaleglosc3190dni;
    
    @Column(length = 255)
    private String roZaleglosc91180dni;
    
    @Column(length = 255)
    private String roZaleglosc180dni;   
    
    @Column(length = 255)
    private String roWindykacja;
    
    @Column(length = 255)
    private String roEgzekucja;    

    @OneToMany(mappedBy="bik", fetch = FetchType.EAGER)
    private Set<BikZapytanie> zapytania;
    
    @OneToMany(mappedBy="bik", fetch = FetchType.EAGER)
    private Set<BikRachunek> rachunki;
    
    @ManyToOne
    @JoinColumn(name="clientId")
    private Client client;
    
    public Integer getIdBik() {
        return idBik;
    }

    public void setIdBik(Integer idBik) {
        this.idBik = idBik;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getOcenaPunktowa() {
        return ocenaPunktowa;
    }

    public void setOcenaPunktowa(String ocenaPunktowa) {
        this.ocenaPunktowa = ocenaPunktowa;
    }

    public String getKlasa() {
        return klasa;
    }

    public void setKlasa(String klasa) {
        this.klasa = klasa;
    }

    public String getRachunkiKwestionowane() {
        return rachunkiKwestionowane;
    }

    public void setRachunkiKwestionowane(String rachunkiKwestionowane) {
        this.rachunkiKwestionowane = rachunkiKwestionowane;
    }

    public String getZablokowanerekordyBIORK() {
        return zablokowanerekordyBIORK;
    }

    public void setZablokowanerekordyBIORK(String zablokowanerekordyBIORK) {
        this.zablokowanerekordyBIORK = zablokowanerekordyBIORK;
    }

    public String getRzZaleglosc030dni() {
        return rzZaleglosc030dni;
    }

    public void setRzZaleglosc030dni(String rzZaleglosc030dni) {
        this.rzZaleglosc030dni = rzZaleglosc030dni;
    }

    public String getRzUmorzone() {
        return rzUmorzone;
    }

    public void setRzUmorzone(String rzUmorzone) {
        this.rzUmorzone = rzUmorzone;
    }

    public String getRzOdzyskane() {
        return rzOdzyskane;
    }

    public void setRzOdzyskane(String rzOdzyskane) {
        this.rzOdzyskane = rzOdzyskane;
    }

    public String getRoZaleglosc030dni() {
        return roZaleglosc030dni;
    }

    public void setRoZaleglosc030dni(String roZaleglosc030dni) {
        this.roZaleglosc030dni = roZaleglosc030dni;
    }

    public String getRoZaleglosc3190dni() {
        return roZaleglosc3190dni;
    }

    public void setRoZaleglosc3190dni(String roZaleglosc3190dni) {
        this.roZaleglosc3190dni = roZaleglosc3190dni;
    }


    public String getRoZaleglosc91180dni() {
        return roZaleglosc91180dni;
    }

    public void setRoZaleglosc91180dni(String roZaleglosc91180dni) {
        this.roZaleglosc91180dni = roZaleglosc91180dni;
    }

    public String getRoZaleglosc180dni() {
        return roZaleglosc180dni;
    }

    public void setRoZaleglosc180dni(String roZaleglosc180dni) {
        this.roZaleglosc180dni = roZaleglosc180dni;
    }

    public String getRoWindykacja() {
        return roWindykacja;
    }

    public void setRoWindykacja(String roWindykacja) {
        this.roWindykacja = roWindykacja;
    }

    public String getRoEgzekucja() {
        return roEgzekucja;
    }

    public void setRoEgzekucja(String roEgzekucja) {
        this.roEgzekucja = roEgzekucja;
    }

    public Set<BikZapytanie> getZapytania() {
        return zapytania;
    }

    public void setZapytania(Set<BikZapytanie> zapytania) {
        this.zapytania = zapytania;
    }

    public Set<BikRachunek> getRachunki() {
        return rachunki;
    }

    public void setRachunki(Set<BikRachunek> rachunki) {
        this.rachunki = rachunki;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
