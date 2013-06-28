package sql.entity;
// Generated 2013-06-27 14:28:15 by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * KlienciKredytyId generated by hbm2java
 */
@Embeddable
public class KlienciKredytyId  implements java.io.Serializable {


     private Klienci klienci;
     private Kredyty kredyty;

    public KlienciKredytyId() {
    }

    @ManyToOne
    public Klienci getKlienci() {
        return klienci;
    }
    public void setKlienci(Klienci klienci) {
        this.klienci = klienci;
    }

    
    @ManyToOne
    public Kredyty getKredyty() {
        return kredyty;
    }
    
    public void setKredyty(Kredyty kredyty) {
        this.kredyty = kredyty;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof KlienciKredytyId) ) return false;
		 KlienciKredytyId castOther = ( KlienciKredytyId ) other; 
         
		 return (this.getKlienci()==castOther.getKlienci())
 && (this.getKredyty()==castOther.getKredyty());
   }
   
   public int hashCode() {
         int result;
         
         result = (klienci != null ? klienci.hashCode() : 0);
         result = 37 * result + (klienci != null ? klienci.hashCode() : 0);
         return result;
   }   


}


