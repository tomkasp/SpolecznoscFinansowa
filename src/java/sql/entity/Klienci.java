package sql.entity;
// Generated 2013-06-24 11:55:43 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Klienci generated by hbm2java
 */
@Entity
@Table(name="klienci"
    ,catalog="System4"
)
public class Klienci  implements java.io.Serializable {


     private Integer idKlienci;
     private Uzytkownik uzytkownik;
     private String imie;
     private String nazwisko;
     private String pesel;
     private String seriaDowodu;
     private String nrDowodu;
     private String miejscowosc;
     private String kodPocztowy;
     private String poczta;
     private String ulica;
     private String nrDomu;
     private String nrMieszkania;
     private Date dataDodaniaKlienta;
     private Set<Kredyty> kredyties = new HashSet<Kredyty>(0);

    public Klienci() {
    }

	
    public Klienci(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
    public Klienci(Uzytkownik uzytkownik, String imie, String nazwisko, String pesel, String seriaDowodu, String nrDowodu, String miejscowosc, String kodPocztowy, String poczta, String ulica, String nrDomu, String nrMieszkania, Date dataDodaniaKlienta, Set<Kredyty> kredyties) {
       this.uzytkownik = uzytkownik;
       this.imie = imie;
       this.nazwisko = nazwisko;
       this.pesel = pesel;
       this.seriaDowodu = seriaDowodu;
       this.nrDowodu = nrDowodu;
       this.miejscowosc = miejscowosc;
       this.kodPocztowy = kodPocztowy;
       this.poczta = poczta;
       this.ulica = ulica;
       this.nrDomu = nrDomu;
       this.nrMieszkania = nrMieszkania;
       this.dataDodaniaKlienta = dataDodaniaKlienta;
       this.kredyties = kredyties;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idKlienci", unique=true, nullable=false)
    public Integer getIdKlienci() {
        return this.idKlienci;
    }
    
    public void setIdKlienci(Integer idKlienci) {
        this.idKlienci = idKlienci;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_uzytkownik", nullable=false)
    public Uzytkownik getUzytkownik() {
        return this.uzytkownik;
    }
    
    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
    
    @Column(name="imie", length=20)
    public String getImie() {
        return this.imie;
    }
    
    public void setImie(String imie) {
        this.imie = imie;
    }
    
    @Column(name="nazwisko", length=40)
    public String getNazwisko() {
        return this.nazwisko;
    }
    
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    @Column(name="pesel", length=11)
    public String getPesel() {
        return this.pesel;
    }
    
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
    
    @Column(name="seriaDowodu", length=3)
    public String getSeriaDowodu() {
        return this.seriaDowodu;
    }
    
    public void setSeriaDowodu(String seriaDowodu) {
        this.seriaDowodu = seriaDowodu;
    }
    
    @Column(name="nrDowodu", length=6)
    public String getNrDowodu() {
        return this.nrDowodu;
    }
    
    public void setNrDowodu(String nrDowodu) {
        this.nrDowodu = nrDowodu;
    }
    
    @Column(name="miejscowosc", length=45)
    public String getMiejscowosc() {
        return this.miejscowosc;
    }
    
    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }
    
    @Column(name="kodPocztowy", length=6)
    public String getKodPocztowy() {
        return this.kodPocztowy;
    }
    
    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }
    
    @Column(name="poczta", length=45)
    public String getPoczta() {
        return this.poczta;
    }
    
    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }
    
    @Column(name="ulica", length=45)
    public String getUlica() {
        return this.ulica;
    }
    
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }
    
    @Column(name="nrDomu", length=5)
    public String getNrDomu() {
        return this.nrDomu;
    }
    
    public void setNrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
    }
    
    @Column(name="nrMieszkania", length=5)
    public String getNrMieszkania() {
        return this.nrMieszkania;
    }
    
    public void setNrMieszkania(String nrMieszkania) {
        this.nrMieszkania = nrMieszkania;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="dataDodaniaKlienta", length=10)
    public Date getDataDodaniaKlienta() {
        return this.dataDodaniaKlienta;
    }
    
    public void setDataDodaniaKlienta(Date dataDodaniaKlienta) {
        this.dataDodaniaKlienta = dataDodaniaKlienta;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="klienci")
    public Set<Kredyty> getKredyties() {
        return this.kredyties;
    }
    
    public void setKredyties(Set<Kredyty> kredyties) {
        this.kredyties = kredyties;
    }




}


