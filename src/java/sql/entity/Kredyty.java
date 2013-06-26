package sql.entity;
// Generated 2013-06-25 09:57:55 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Kredyty generated by hbm2java
 */
@Entity
@Table(name="kredyty"
    ,catalog="System4"
)
public class Kredyty  implements java.io.Serializable {


     private Integer idkredyty;
     private Klienci klienci;
     private String nazwaBanku;
     private String nrUmowyPosrednictwa;
     private BigDecimal kwotaKredytuBrutto;
     private Integer prowizjaBankuWprocentach;
     private BigDecimal prowizjaBankuWpln;
     private BigDecimal ubezpieczenieWpln;
     private BigDecimal kosztaWpln;
     private Integer swotWprocentach;
     private BigDecimal swotWpln;
     private BigDecimal kwotaKonsolidacji;
     private Integer okresKredytowaniaWmc;
     private BigDecimal rataWpln;
     private Integer oprocentowanieWprocentach;
     private BigDecimal wolnaGotowka;
     private String miejscePodpisaniaDokumentow;
     private Date dataMozliwegoUruchomienia;
     private Date dataDodaniaKredytu;
     private Boolean czyWygenerowanoDokumenty;

    public Kredyty() {
    }

	
    public Kredyty(Klienci klienci) {
        this.klienci = klienci;
    }
    public Kredyty(Klienci klienci, String nazwaBanku, String nrUmowyPosrednictwa, BigDecimal kwotaKredytuBrutto, Integer prowizjaBankuWprocentach, BigDecimal prowizjaBankuWpln, BigDecimal ubezpieczenieWpln, BigDecimal kosztaWpln, Integer swotWprocentach, BigDecimal swotWpln, BigDecimal kwotaKonsolidacji, Integer okresKredytowaniaWmc, BigDecimal rataWpln, Integer oprocentowanieWprocentach, BigDecimal wolnaGotowka, String miejscePodpisaniaDokumentow, Date dataMozliwegoUruchomienia, Date dataDodaniaKredytu, Boolean czyWygenerowanoDokumenty) {
       this.klienci = klienci;
       this.nazwaBanku = nazwaBanku;
       this.nrUmowyPosrednictwa = nrUmowyPosrednictwa;
       this.kwotaKredytuBrutto = kwotaKredytuBrutto;
       this.prowizjaBankuWprocentach = prowizjaBankuWprocentach;
       this.prowizjaBankuWpln = prowizjaBankuWpln;
       this.ubezpieczenieWpln = ubezpieczenieWpln;
       this.kosztaWpln = kosztaWpln;
       this.swotWprocentach = swotWprocentach;
       this.swotWpln = swotWpln;
       this.kwotaKonsolidacji = kwotaKonsolidacji;
       this.okresKredytowaniaWmc = okresKredytowaniaWmc;
       this.rataWpln = rataWpln;
       this.oprocentowanieWprocentach = oprocentowanieWprocentach;
       this.wolnaGotowka = wolnaGotowka;
       this.miejscePodpisaniaDokumentow = miejscePodpisaniaDokumentow;
       this.dataMozliwegoUruchomienia = dataMozliwegoUruchomienia;
       this.dataDodaniaKredytu = dataDodaniaKredytu;
       this.czyWygenerowanoDokumenty = czyWygenerowanoDokumenty;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idkredyty", unique=true, nullable=false)
    public Integer getIdkredyty() {
        return this.idkredyty;
    }
    
    public void setIdkredyty(Integer idkredyty) {
        this.idkredyty = idkredyty;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="klienci_idKlienci", nullable=false)
    public Klienci getKlienci() {
        return this.klienci;
    }
    
    public void setKlienci(Klienci klienci) {
        this.klienci = klienci;
    }
    
    @Column(name="nazwaBanku", length=60)
    public String getNazwaBanku() {
        return this.nazwaBanku;
    }
    
    public void setNazwaBanku(String nazwaBanku) {
        this.nazwaBanku = nazwaBanku;
    }
    
    @Column(name="nrUmowyPosrednictwa", length=30)
    public String getNrUmowyPosrednictwa() {
        return this.nrUmowyPosrednictwa;
    }
    
    public void setNrUmowyPosrednictwa(String nrUmowyPosrednictwa) {
        this.nrUmowyPosrednictwa = nrUmowyPosrednictwa;
    }
    
    @Column(name="kwotaKredytuBrutto", precision=12)
    public BigDecimal getKwotaKredytuBrutto() {
        return this.kwotaKredytuBrutto;
    }
    
    public void setKwotaKredytuBrutto(BigDecimal kwotaKredytuBrutto) {
        this.kwotaKredytuBrutto = kwotaKredytuBrutto;
    }
    
    @Column(name="prowizjaBankuWProcentach")
    public Integer getProwizjaBankuWprocentach() {
        return this.prowizjaBankuWprocentach;
    }
    
    public void setProwizjaBankuWprocentach(Integer prowizjaBankuWprocentach) {
        this.prowizjaBankuWprocentach = prowizjaBankuWprocentach;
    }
    
    @Column(name="prowizjaBankuWPLN", precision=12)
    public BigDecimal getProwizjaBankuWpln() {
        return this.prowizjaBankuWpln;
    }
    
    public void setProwizjaBankuWpln(BigDecimal prowizjaBankuWpln) {
        this.prowizjaBankuWpln = prowizjaBankuWpln;
    }
    
    @Column(name="ubezpieczenieWPLN", precision=12)
    public BigDecimal getUbezpieczenieWpln() {
        return this.ubezpieczenieWpln;
    }
    
    public void setUbezpieczenieWpln(BigDecimal ubezpieczenieWpln) {
        this.ubezpieczenieWpln = ubezpieczenieWpln;
    }
    
    @Column(name="kosztaWPLN", precision=12)
    public BigDecimal getKosztaWpln() {
        return this.kosztaWpln;
    }
    
    public void setKosztaWpln(BigDecimal kosztaWpln) {
        this.kosztaWpln = kosztaWpln;
    }
    
    @Column(name="swotWprocentach")
    public Integer getSwotWprocentach() {
        return this.swotWprocentach;
    }
    
    public void setSwotWprocentach(Integer swotWprocentach) {
        this.swotWprocentach = swotWprocentach;
    }
    
    @Column(name="swotWpln", precision=12)
    public BigDecimal getSwotWpln() {
        return this.swotWpln;
    }
    
    public void setSwotWpln(BigDecimal swotWpln) {
        this.swotWpln = swotWpln;
    }
    
    @Column(name="kwotaKonsolidacji", precision=12)
    public BigDecimal getKwotaKonsolidacji() {
        return this.kwotaKonsolidacji;
    }
    
    public void setKwotaKonsolidacji(BigDecimal kwotaKonsolidacji) {
        this.kwotaKonsolidacji = kwotaKonsolidacji;
    }
    
    @Column(name="okresKredytowaniaWmc")
    public Integer getOkresKredytowaniaWmc() {
        return this.okresKredytowaniaWmc;
    }
    
    public void setOkresKredytowaniaWmc(Integer okresKredytowaniaWmc) {
        this.okresKredytowaniaWmc = okresKredytowaniaWmc;
    }
    
    @Column(name="rataWPLN", precision=12)
    public BigDecimal getRataWpln() {
        return this.rataWpln;
    }
    
    public void setRataWpln(BigDecimal rataWpln) {
        this.rataWpln = rataWpln;
    }
    
    @Column(name="oprocentowanieWProcentach")
    public Integer getOprocentowanieWprocentach() {
        return this.oprocentowanieWprocentach;
    }
    
    public void setOprocentowanieWprocentach(Integer oprocentowanieWprocentach) {
        this.oprocentowanieWprocentach = oprocentowanieWprocentach;
    }
    
    @Column(name="wolnaGotowka", precision=12)
    public BigDecimal getWolnaGotowka() {
        return this.wolnaGotowka;
    }
    
    public void setWolnaGotowka(BigDecimal wolnaGotowka) {
        this.wolnaGotowka = wolnaGotowka;
    }
    
    @Column(name="miejscePodpisaniaDokumentow", length=20)
    public String getMiejscePodpisaniaDokumentow() {
        return this.miejscePodpisaniaDokumentow;
    }
    
    public void setMiejscePodpisaniaDokumentow(String miejscePodpisaniaDokumentow) {
        this.miejscePodpisaniaDokumentow = miejscePodpisaniaDokumentow;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="dataMozliwegoUruchomienia", length=10)
    public Date getDataMozliwegoUruchomienia() {
        return this.dataMozliwegoUruchomienia;
    }
    
    public void setDataMozliwegoUruchomienia(Date dataMozliwegoUruchomienia) {
        this.dataMozliwegoUruchomienia = dataMozliwegoUruchomienia;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="dataDodaniaKredytu", length=10)
    public Date getDataDodaniaKredytu() {
        return this.dataDodaniaKredytu;
    }
    
    public void setDataDodaniaKredytu(Date dataDodaniaKredytu) {
        this.dataDodaniaKredytu = dataDodaniaKredytu;
    }
    
    @Column(name="czyWygenerowanoDokumenty")
    public Boolean getCzyWygenerowanoDokumenty() {
        return this.czyWygenerowanoDokumenty;
    }
    
    public void setCzyWygenerowanoDokumenty(Boolean czyWygenerowanoDokumenty) {
        this.czyWygenerowanoDokumenty = czyWygenerowanoDokumenty;
    }




}


