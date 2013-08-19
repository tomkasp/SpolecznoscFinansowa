/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.ProductType;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author XaI
 */
@ManagedBean
@ViewScoped
public class ConsultantProfileMB {

    @ManagedProperty(value="clientCaseMB")
    private ClientCaseMB clientCaseMB;
    
    private ArrayList<ClientCase> casesRated = new ArrayList();
       
    public ConsultantProfileMB() {
        
    }
    
    @PostConstruct
    public void loadData()
    {
        casesRated = (ArrayList<ClientCase>) new CaseRatingDAO().getConsultantRatings(clientCaseMB.getSelectedConsultant().getIdConsultant());
    }

    
    
    public ArrayList<Institution> castInstitutionSetToArray(Set<Institution> csSet)
    {
        return new ArrayList<Institution>(csSet);
    }
    
    public ArrayList<ProductType> castProductTypeSetToArray(Set<ProductType> csSet)
    {
        return new ArrayList<ProductType>(csSet);
    }
    
    public ArrayList<Institution> selectBanksFromConsultant(Consultant cons)
    {
       ArrayList<Institution> banks = new ArrayList();
       for (Institution i : cons.getInstitutions())
       {
           if (i.getType().equals(0))
           {
               banks.add(i);
           }
       }
       
       return banks;
    }
    
    public ArrayList<Institution> selectOtherInsFromConsultant(Consultant cons)
    {
       ArrayList<Institution> other = new ArrayList();
       for (Institution i : cons.getInstitutions())
       {
           if (i.getType().equals(1))
           {
               other.add(i);
           }
       }
       
       return other;
    }

    public ArrayList<ClientCase> getCasesRated() {
        return casesRated;
    }

    public void setCasesRated(ArrayList<ClientCase> casesRated) {
        this.casesRated = casesRated;
    }
      
    
}
