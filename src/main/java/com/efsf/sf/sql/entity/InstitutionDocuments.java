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
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table
public class InstitutionDocuments implements Serializable {
   
  @Id 
  @GeneratedValue(strategy=IDENTITY)
  @Column(name="id_wpisu", unique=true, nullable=false)
  private Integer idDocument;

  @Column
  private String fileName;
  @Column
  private String description;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_institution")
  @ForeignKey(name = "productToInstitution")
  private Institution institution;
  
    public InstitutionDocuments(){
        
    }
  
    public InstitutionDocuments(String description, String fileName, Institution institution){
       this.fileName=fileName;
       this.description=description;
       this.institution=institution;
    }

    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    
}
