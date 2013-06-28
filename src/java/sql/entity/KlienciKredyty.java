package sql.entity;
// Generated 2013-06-27 14:28:15 by Hibernate Tools 3.2.1.GA


import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * KlienciKredyty generated by hbm2java
 */
@Entity
@Table(name="klienciKredyty"
    ,catalog="System4"
)

@AssociationOverrides({
    @AssociationOverride(name="id.klienci", joinColumns = @JoinColumn(name="klienci_id")),
    @AssociationOverride(name="id.kredyty", joinColumns = @JoinColumn(name="kredyty_id"))
})

public class KlienciKredyty  implements java.io.Serializable {


     private KlienciKredytyId id = new KlienciKredytyId();
     private Boolean wspolkredytobiorca;

    public KlienciKredyty() {
    }

   
     @EmbeddedId
    
    public KlienciKredytyId getId() {
        return this.id;
    }
    public void setId(KlienciKredytyId id) {
        this.id = id;
    }
    
    @Transient
    public Kredyty getKredyty() {
        return getId().getKredyty();
    }
    public void setKredyty(Kredyty kredyty) {
        getId().setKredyty(kredyty);
    }
    
    @Transient
    public Klienci getKlienci() {
        return getId().getKlienci();
    }
    public void setKlienci(Klienci klienci) {
        getId().setKlienci(klienci);
    }
    
    
    @Column(name="wspolkredytobiorca")
    public Boolean getWspolkredytobiorca() {
        return this.wspolkredytobiorca;
    }
    
    public void setWspolkredytobiorca(Boolean wspolkredytobiorca) {
        this.wspolkredytobiorca = wspolkredytobiorca;
    }




}


