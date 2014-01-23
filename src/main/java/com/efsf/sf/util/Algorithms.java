package com.efsf.sf.util;

import com.efsf.sf.sql.dao.AmountHistoryDAO;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Bik;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.parsers.ParserCSV;
import java.util.Iterator;
import java.util.List;

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
        
        // BIK is really useful in getting credits 
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
        
        System.out.println("Difficulty from data: " +  difficulty);
        
        difficulty += calculateDifficultyFromBankAccountAnalysis(client);
        
        System.out.println("Difficulty with CSV: " +  difficulty);
        
        difficulty += calculateDifficultyFromBIKAnalysis(client);
        
        System.out.println("Difficulty with BIK: " +  difficulty);
      
        return difficulty/3;
    }
    
    
    public static double calculateDifficultyFromBankAccountAnalysis(Client client)
    {
         int difficulty = 0;
         AmountHistoryDAO dao = new AmountHistoryDAO();
         ParserCSV parser = new ParserCSV();
         
         List<AmountHistory> list = dao.getWhere("fkClient", client.getIdClient());
         if (list == null || list.isEmpty())
         {
             difficulty += 10;
         }
         else
         {
             String creditChance = parser.calculateClientCreditChance(client);
             
             switch (creditChance)
             {
                 case ("Bardzo małe"): 
                     difficulty += 4;
                     break;
                 case ("Małe"):
                     difficulty += 3;
                     break;
                 case ("Średnie"):
                     difficulty += 2; 
                     break;
                 case ("Duże"):
                     difficulty += 0;
                     break;
             }
             
             String qualityOfLife = parser.calculateClientQualityOfLife(client);
             
             switch (qualityOfLife)
             {
                 case ("Niski"): 
                     difficulty += 3;
                     break;
                 case ("Średni"):
                     difficulty += 2;
                     break;
                 case ("Wysoki"):
                     difficulty += 1; 
                 case ("Bardzo wysoki"):
                     difficulty += 0;
             }
         }
         return difficulty;
    }
    
    public static int calculateDifficultyFromBIKAnalysis(Client client)
    {
        int difficulty = 0;
        
        GenericDao<Bik> dao = new GenericDao(Bik.class);
        
        List<Bik> list = dao.getWhere("clientId", client.getIdClient());
        
        if (list.isEmpty())
        {
            difficulty += 10;
        }
        else
        {
             Bik b = list.get(0);
             
             String rankAsString = b.getBikRank();
             String bikClass = b.getBikClass();
             
             if (rankAsString != null)
             {
                 Integer rank = Integer.parseInt(rankAsString);
                 difficulty +=  7 - (int) (rank/100.0);           
                 return difficulty < 0 ? 0 : difficulty;
             }
             else if ((bikClass != null))
             {
                 char letter = bikClass.toUpperCase().charAt(0);
                 
                 switch(letter)
                 {
                     case 'A':
                         difficulty += 2;
                         break;
                     case 'B': 
                         difficulty += 4;
                         break;
                     case 'C': 
                         difficulty += 6;
                         break;
                     case 'D': 
                         difficulty += 8;
                         break;
                 }
                     
             }   
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
