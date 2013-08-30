package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.client.CaseViewMB;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.ProductType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ConsultantProfileMB implements Serializable{

    @ManagedProperty(value="#{caseViewMB}")
    private CaseViewMB caseViewMB;
    
    private Integer idConsultant;
    
    private Consultant selectedConsultant;
    
    private List<ClientCase> casesRated = new ArrayList();
       

    public void loadData()
    {
        casesRated = (ArrayList<ClientCase>) new CaseRatingDAO().getConsultantRatings(idConsultant);
        selectedConsultant = new ConsultantDAO().readConsultantForSettings(idConsultant);
    }

    
    
    public List<Institution> castInstitutionSetToArray(Set<Institution> csSet)
    {
        return new ArrayList(csSet);
    }
    
    public List<ProductType> castProductTypeSetToArray(Set<ProductType> csSet)
    {
        return new ArrayList(csSet);
    }
    
    public List<Institution> selectBanksFromConsultant(Consultant cons)
    {
       ArrayList<Institution> banks = new ArrayList();
       if (cons.getInstitutions() != null)
       {
            for (Institution i : cons.getInstitutions())
            {
                if (i.getType().equals(0))
                {
                    banks.add(i);
                }
            }
            return banks;
       }
       else {
           return new ArrayList();
       }
       
       
    }
    
    public List<Institution> selectOtherInsFromConsultant(Consultant cons)
    {
       ArrayList<Institution> other = new ArrayList();
       if (cons.getInstitutions() != null)
       {
        for (Institution i : cons.getInstitutions())
        {
            if (i.getType().equals(1))
            {
                other.add(i);
            }
        }
        return other;
       }
       else {
           return new ArrayList();
       }
    }

    public List<ClientCase> getCasesRated() {
        return casesRated;
    }

    public void setCasesRated(List<ClientCase> casesRated) {
        this.casesRated = casesRated;
    }

    public CaseViewMB getCaseViewMB() {
        return caseViewMB;
    }

    public void setCaseViewMB(CaseViewMB caseViewMB) {
        this.caseViewMB = caseViewMB;
    }

    public Consultant getSelectedConsultant() {
        return selectedConsultant;
    }

    public void setSelectedConsultant(Consultant selectedConsultant) {
        this.selectedConsultant = selectedConsultant;
    }

    public Integer getIdConsultant() {
        return idConsultant;
    }

    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
    }

      
    
}
