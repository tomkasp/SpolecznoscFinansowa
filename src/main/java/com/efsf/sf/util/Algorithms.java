package com.efsf.sf.util;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.entity.RequiredDocuments;
import java.util.Iterator;

/**
 *
 * @author XaI
 */
public class Algorithms 
{
    //Some kind of prototype algorithm to calculate difficulty of the case 
    public static int calculateCaseDifficulty(Client client, ClientCase clientCase)
    {
         
        RequiredDocuments requiredDocuments = new RequiredDocumentsDAO().readForFkClient(client.getIdClient());
        
        int difficulty = 1;
        
        // BIK is really usful in getting credits 
        if (requiredDocuments == null ||  requiredDocuments.getBik() == null  )
        {
            difficulty++;
        }
        
        // If client have some obligations the loan is harder to get
        if (client.getObligations() != null)
        {
            if (client.getObligations().size() == 1) {
                difficulty++;
            }
            else if (client.getObligations().size() > 1) {
                difficulty += 2;
            }
        }
        
        // Chwilówka should be here || We assume that 'chwilówka' is easier to get then other loans
        if (clientCase.getProductType().getIdProductType() != Settings.CHWILOWKA )
        {
            difficulty+=2;
        }
        
        // The lower income is the harder is to get credit
        if(calculateClientIncome(client) < 2000.0 )
        {
            difficulty++;
        }
        
        //The age have some infuence on income too. 
        Converters c = new Converters();
        
        if(c.ageFromBirthDate(client.getBirthDate()) <  22 )
        {
            difficulty++;
        }   
        else if(c.ageFromBirthDate(client.getBirthDate()) > 65)
        {
            difficulty++;
        }
        
        //If client have not used our system yet and have no case succesfully finished
        if (!new ClientCaseDAO().checkClientFinishedCases(client.getIdClient()))
        {
            difficulty++;
        }    
        else if (!new ClientCaseDAO().checkClientCases(client.getIdClient()))
        {
            difficulty++;
        }
      
        return difficulty;
    }
    
    public static double calculateClientIncome(Client client)
    {
       double income = 0;
       
       for (Income i : client.getIncomes())
       {
           income += i.getMonthlyNetto().doubleValue();
       }
       
       for (IncomeBusinessActivity iba : client.getIncomeBusinessActivities())
       {
           income += iba.getIncomeLastYearNetto().doubleValue();
       }
       
       return income; 
    }
    
    
    public static int calculateProgress(Consultant cs){
        int progress=0;
        
        Consultant consultant = new ConsultantDAO().readConsultantForSettings(cs.getIdConsultant());
        
        Iterator<Address> it = consultant.getAddresses().iterator();
        if( it.hasNext() ){
            Address address = it.next();
            if (address.getType() == 2 && it.hasNext())
            {
                address = it.next();
            }
            if (address.getType() == 1)
            {
                progress+= getProgressForAddress(address);
            }
        }
        
        if(consultant.getInstitutions()!=null && consultant.getInstitutions().size()>0){
            progress+=10;
        }

        if(consultant.getExpirience()!=null && !consultant.getExpirience().equals("")){
            progress+=10;
        }
        
        if(consultant.getProductTypes()!=null && consultant.getProductTypes().size()>0){
            progress+=10;
        }              

        return progress;
    }
    
    //Prototype of algorithm to calculate progress ('postęp') -> I was told it was connected to amount of data filled 
    public static int calculateProgress(Integer clId)
    {
         Client client;
         client = new ClientDAO().readClientForSettings(clId);
         int progress = 0;
         
         if(client.getMaritalStatus().getIdMaritalStatus() != 0)
         {
             progress+=5;
         }
         if(client.getEducation().getIdEducation() != 0)
         {
             progress+=5;
         }
         if(client.getSex() != null)
         {
             progress+=5;
         }
         if(client.getPesel() != null)
         {
             progress+=5;
         }
         if(client.getBirthDate() != null)
         {
             progress+=5;
         }
         if(client.getLastName() != null && !client.getLastName().equals(""))
         {
             progress+=5;
         }
         
         Address address = client.getAddresses().iterator().next();
         progress+= getProgressForAddress(address);
         
         RequiredDocuments requiredDocuments = new RequiredDocumentsDAO().readForFkClient(client.getIdClient());

         if (requiredDocuments != null)
         {
             if(requiredDocuments.getIdCard() != null)
             {
                 progress+=10;
             }
             if(requiredDocuments.getBik() != null)
             {
                 progress+=10;
             }
             if(requiredDocuments.getIncomeStatement() != null)
             {
                 progress+=10;
             }
             if(requiredDocuments.getTitleDeed() != null)
             {
                 progress+=10;
             }      
         }
     
         return progress;
    }

    private static int getProgressForAddress(Address address) {
        int progress=0;
        
        if (address.getZipCode() != null && !"".equals(address.getZipCode()))
        {
            progress+=5;
        }
        if (address.getRegion().getIdRegion() != 0)
        {
            progress+=5;
        }
        if(address.getCity() != null && !"".equals(address.getCity()))
        {
            progress+=5;
        }
        if(address.getHouseNumber() != null && !"".equals(address.getHouseNumber()))
        {
            progress+=5;
        }
        if(address.getPhone() != null && !"".equals(address.getPhone()))
        {
            progress+=5;
        }
        if(address.getCity() != null && !"".equals(address.getPhone()))
        {
            progress+=5;
        }
        return progress;
    }
    
}
