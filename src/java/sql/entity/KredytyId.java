package sql.entity;
// Generated 2013-06-03 09:57:46 by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * KredytyId generated by hbm2java
 */
@Embeddable
public class KredytyId  implements java.io.Serializable {


     private int idkredyty;
     private int klienciIdKlienci;

    public KredytyId() {
    }

    public KredytyId(int idkredyty, int klienciIdKlienci) {
       this.idkredyty = idkredyty;
       this.klienciIdKlienci = klienciIdKlienci;
    }
   

    @Column(name="idkredyty", nullable=false)
    public int getIdkredyty() {
        return this.idkredyty;
    }
    
    public void setIdkredyty(int idkredyty) {
        this.idkredyty = idkredyty;
    }

    @Column(name="klienci_idKlienci", nullable=false)
    public int getKlienciIdKlienci() {
        return this.klienciIdKlienci;
    }
    
    public void setKlienciIdKlienci(int klienciIdKlienci) {
        this.klienciIdKlienci = klienciIdKlienci;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof KredytyId) ) return false;
		 KredytyId castOther = ( KredytyId ) other; 
         
		 return (this.getIdkredyty()==castOther.getIdkredyty())
 && (this.getKlienciIdKlienci()==castOther.getKlienciIdKlienci());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdkredyty();
         result = 37 * result + this.getKlienciIdKlienci();
         return result;
   }   


}


