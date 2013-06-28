package sql.entity;
// Generated 2013-06-27 14:28:15 by Hibernate Tools 3.2.1.GA


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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Uzytkownik generated by hbm2java
 */
@Entity
@Table(name="uzytkownik"
    ,catalog="System4"
)
public class Uzytkownik  implements java.io.Serializable {


     private Integer uzytkownikId;
     private String login;
     private String haslo;
     private String imie;
     private String nazwisko;
     private String rola;
     private String oddzial;
     private Boolean aktywne;
     private Date dataUtworzenia;
     private Set<Klienci> kliencis = new HashSet<Klienci>(0);

    public Uzytkownik() {
    }

	
    public Uzytkownik(String login, String haslo) {
        this.login = login;
        this.haslo = haslo;
    }
    public Uzytkownik(String login, String haslo, String imie, String nazwisko, String rola, String oddzial, Boolean aktywne, Date dataUtworzenia, Set<Klienci> kliencis) {
       this.login = login;
       this.haslo = haslo;
       this.imie = imie;
       this.nazwisko = nazwisko;
       this.rola = rola;
       this.oddzial = oddzial;
       this.aktywne = aktywne;
       this.dataUtworzenia = dataUtworzenia;
       this.kliencis = kliencis;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="uzytkownik_id", unique=true, nullable=false)
    public Integer getUzytkownikId() {
        return this.uzytkownikId;
    }
    
    public void setUzytkownikId(Integer uzytkownikId) {
        this.uzytkownikId = uzytkownikId;
    }
    
    @Column(name="login", nullable=false, length=30)
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    @Column(name="haslo", nullable=false, length=45)
    public String getHaslo() {
        return this.haslo;
    }
    
    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
    
    @Column(name="imie", length=30)
    public String getImie() {
        return this.imie;
    }
    
    public void setImie(String imie) {
        this.imie = imie;
    }
    
    @Column(name="nazwisko", length=45)
    public String getNazwisko() {
        return this.nazwisko;
    }
    
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    @Column(name="rola", length=20)
    public String getRola() {
        return this.rola;
    }
    
    public void setRola(String rola) {
        this.rola = rola;
    }
    
    @Column(name="oddzial", length=2)
    public String getOddzial() {
        return this.oddzial;
    }
    
    public void setOddzial(String oddzial) {
        this.oddzial = oddzial;
    }
    
    @Column(name="aktywne")
    public Boolean getAktywne() {
        return this.aktywne;
    }
    
    public void setAktywne(Boolean aktywne) {
        this.aktywne = aktywne;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dataUtworzenia", length=19)
    public Date getDataUtworzenia() {
        return this.dataUtworzenia;
    }
    
    public void setDataUtworzenia(Date dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="uzytkownik")
    public Set<Klienci> getKliencis() {
        return this.kliencis;
    }
    
    public void setKliencis(Set<Klienci> kliencis) {
        this.kliencis = kliencis;
    }




}


