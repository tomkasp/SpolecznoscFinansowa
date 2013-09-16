package com.efsf.sf.util.bik;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Bik;
import com.efsf.sf.sql.entity.BikAccountHistory;
import com.efsf.sf.sql.entity.BikAccount;
import com.efsf.sf.sql.entity.BikQuestion;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.RequiredDocuments;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alghorithm extends Thread{

    List<RuleBase> rules = new ArrayList<RuleBase>();
    private String file;
    private String password;
    
    private ArrayList<Table> tables= new ArrayList<Table>();
    private Map<String, Object> result = new HashMap<String, Object>();
    private Integer clientId; 
    
    //private static String path="C:\\files\\";
    private static String path="/home/sf/bik/";    
    
    private String bikSample="Biuro Informacji Kredytowej S.A.";
    
    public Alghorithm(String file, String password, Integer clientId) {
        setUpRules();
        this.file=file;
        this.password=password;
        this.clientId=clientId;
    }
    
    @Override
    public void run(){
        try {
            PDFWithText pdf = new PDFWithText();
            pdf.reset();
            String text = pdf.getTextFromPDF(getPath() + file, password);
            
            //Poprawny BIK
            if(text.length()>1000 && text.contains(bikSample)){

                parse(preProcess(text));
                tables=pdf.getTables(); 
                saveResult();
                setStatus(4);
                
            //Nie poprawny BIK    
            } else {
                setStatus(3);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Alghorithm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Alghorithm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            File f=new File(getPath()+file);
            f.delete();
        }
    }
    

    private void parse(String text) {
        Matcher m;
        for (int i = 0; i < rules.size(); i++) {
           // if(rules.get(i).getType()==RuleBase.T_MANY_TABLE || rules.get(i).getType()==RuleBase.T_ONE_TABLE){
               m = Pattern.compile(rules.get(i).getRule(), Pattern.DOTALL).matcher(text); 
           // } else {
          //     m = Pattern.compile(rules.get(i).getRule()).matcher(text); 
         //   }
             
            
            if (rules.get(i).getType() == RuleBase.T_MANY) {
               List<String> ls=new ArrayList<>(); 
               while(m.find()) {
                    ls.add(trim(m.group("val")));
                }
               
               if(!ls.isEmpty()) {
                    getResult().put(rules.get(i).getName(), ls);
                }
               
            } else if (rules.get(i).getType() == RuleBase.T_ONE) {
                
                if (m.find()) {
                    getResult().put(rules.get(i).getName(), trim(m.group("val")));
                }
                
            } else if (rules.get(i).getType() == RuleBase.T_ONE_TABLE) {
                
                if (m.find()) {
                    getResult().put(rules.get(i).getName(), TableHelper.getAsTable(m.group("val")));
                }
                
            } else if (rules.get(i).getType() == RuleBase.T_MANY_TABLE) {
                 
                 while(m.find()) {
                    getResult().put(rules.get(i).getName(), TableHelper.getAsTable(m.group("val")));
                } 
              
            }    
            m.reset();
        }
    }
    
    public String trim(String text){
        return text.replaceAll("\\n", " ").trim();
    }
    
    public String preProcess(String text){
        //remove page footer
        //remove empty lines or lines with semicolon only
        return text.replaceAll("Strona([^\\n]*\\n+)+?Data i czas wydruku([^\\n]*)", "")
                   .replaceAll("(?m)^[ \\t;]*\\r?\\n", "")
                   .replaceAll("Informacje przetwarzane w BIK S.A. po wygaśnięciu zobowiązania wynikającego z umowy z bankiem lub instytucją upoważnioną do udzielania\n" +
                                "kredytów - zgodnie z art. 105a ust. 4 i 5 dla celów stosowania metod statystycznych,\n" +
                                "o których mowa w art. 128 ust. 3 Prawa Bankowego.", "");
    }


    public Map<String, Object> getResult() {
        return result;
    }

    public List<Table> getTables() {
        return tables;
    }

    
        public void saveResult() {
        
        GenericDao<Client> client_dao = new GenericDao(Client.class); 
        Client client=client_dao.getById(clientId);
            
        //Bik główne dane
        Bik bik = new Bik();
        bik.setBikClass((String) result.get(RegexBase.KLASYFIKACJA_BIK.getName()));
        bik.setBikRank((String) result.get(RegexBase.OCENA_PUNKTOWA.getName()));
        bik.setPesel((String) result.get(RegexBase.PESEL.getName()));
        bik.setClosedAccountsArrear030days((String) result.get(RegexBase.RACH_ZAMK_ZALEG_0_30.getName()));
        bik.setClosedAccountsExtinguished((String) result.get(RegexBase.RACH_ZAMK_UMORZONY.getName()));
        bik.setClosedAccountsRegained((String) result.get(RegexBase.RACH_ZAMK_ODZYSKANY.getName()));
        bik.setOpenAccountsArrear030days((String) result.get(RegexBase.RACH_OTW_ZALEG_0_30.getName()));
        bik.setOpenAccountsArrear3190days((String) result.get(RegexBase.RACH_OTW_ZALEG_31_90.getName()));
        bik.setOpenAccountsArrear91180days((String) result.get(RegexBase.RACH_OTW_ZALEG_91_180.getName()));
        bik.setOpenAccountsArrear180days((String) result.get(RegexBase.RACH_OTW_ZALEG_180_WIECEJ.getName()));
        bik.setOpenAccountsExecution((String) result.get(RegexBase.RACH_OTW_EGZEKUCJA.getName()));
        bik.setOpenAccountsCollection((String) result.get(RegexBase.RACH_OTW_WINDYKACJA.getName()));
        bik.setImpeachedAccounts((String) result.get(RegexBase.RACHUNKI_KWESTIONOWANE.getName()));
        bik.setClient(client);
        
        GenericDao<Bik> dao = new GenericDao(Bik.class);
        dao.save(bik);

        //Rachunki
        GenericDao<BikAccount> dao2 = new GenericDao(BikAccount.class);
        ArrayList<String> kwoty = (ArrayList<String>) result.get(RegexBase.R_KWOTA.getName());
        if (kwoty != null) {

            for (int i = 0; i < kwoty.size(); i++) {
             BikAccount rachunek=new BikAccount();
             rachunek.setCurrency(((ArrayList<String>) result.get(RegexBase.R_WALUTA.getName())).get(i));
             rachunek.setCreateRelationDate(((ArrayList<String>) result.get(RegexBase.R_DATA_RELACJI.getName())).get(i));
             rachunek.setTransactionType(((ArrayList<String>) result.get(RegexBase.R_TYP_TRANSAKCJI.getName())).get(i));
             rachunek.setClientRelation(((ArrayList<String>) result.get(RegexBase.R_RELACJA.getName())).get(i));
             rachunek.setAmountWithInterestExpense1(((ArrayList<String>) result.get(RegexBase.R_KWOTA.getName())).get(i));
             rachunek.setBank(((ArrayList<String>) result.get(RegexBase.R_BANK.getName())).get(i));
             rachunek.setBik(bik);
             dao2.save(rachunek);
             
                //Zapisz historie rachunku
                GenericDao<BikAccountHistory> dao3 = new GenericDao(BikAccountHistory.class);
                
                for(int j=0; j<tables.get(i).data.size();j++){
                    BikAccountHistory historia=new BikAccountHistory();
                    historia.setAccount(rachunek);
                    historia.setNumber(tables.get(i).data.get(j)[0]);
                    historia.setCreatedDate(tables.get(i).data.get(j)[1]);
                    historia.setStatus(tables.get(i).data.get(j)[2]);
                    historia.setAmount(tables.get(i).data.get(j)[3]);
                    historia.setOverduePayments(tables.get(i).data.get(j)[04]);
                    historia.setPaymentDelay(tables.get(i).data.get(j)[5]);
                    historia.setOverduePayments(tables.get(i).data.get(j)[6]);
                    historia.setLimitBalance(tables.get(i).data.get(j)[7]);
                    historia.setCurrency(tables.get(i).data.get(j)[8]);
                    dao3.save(historia);
                }
                
                
            }
        }
        
        
        //Zapytania
        GenericDao<BikQuestion> dao3 = new GenericDao(BikQuestion.class);
        ArrayList<String> zapytania = (ArrayList<String>) result.get(RegexBase.Z_KWOTA.getName());
        if (zapytania != null) {

            for (int i = 0; i < zapytania.size(); i++) {
             BikQuestion zapytanie=new BikQuestion();
             zapytanie.setQuestionDate(((ArrayList<String>) result.get(RegexBase.Z_DATA.getName())).get(i));
             zapytanie.setAmount(((ArrayList<String>) result.get(RegexBase.Z_KWOTA.getName())).get(i));
             zapytanie.setReason(((ArrayList<String>) result.get(RegexBase.Z_POWOD.getName())).get(i));
             zapytanie.setType(((ArrayList<String>) result.get(RegexBase.Z_TYP.getName())).get(i));
             zapytanie.setBik(bik);
             dao3.save(zapytanie);
            }
        }        
        
        
        System.out.println("");
    }

    private void setUpRules() {
        //BIK
        rules.add(RegexBase.PESEL);
        rules.add(RegexBase.KLASYFIKACJA_BIK);
        rules.add(RegexBase.OCENA_PUNKTOWA);

        rules.add(RegexBase.RACH_ZAMK_ZALEG_0_30);
        rules.add(RegexBase.RACH_ZAMK_UMORZONY);
        rules.add(RegexBase.RACH_ZAMK_ODZYSKANY);
        rules.add(RegexBase.RACH_OTW_ZALEG_0_30);
        rules.add(RegexBase.RACH_OTW_ZALEG_31_90);
        rules.add(RegexBase.RACH_OTW_ZALEG_91_180);
        rules.add(RegexBase.RACH_OTW_ZALEG_180_WIECEJ);

        rules.add(RegexBase.RACH_OTW_WINDYKACJA);
        rules.add(RegexBase.RACH_OTW_EGZEKUCJA);
        rules.add(RegexBase.RACHUNKI_KWESTIONOWANE);
        

        //Rachunki
        rules.add(RegexBase.R_BANK);
        rules.add(RegexBase.R_DATA_RELACJI);
        rules.add(RegexBase.R_KWOTA);
        rules.add(RegexBase.R_RELACJA);
        rules.add(RegexBase.R_TYP_TRANSAKCJI);
        rules.add(RegexBase.R_WALUTA);
        
        //Zapytania
        rules.add(RegexBase.Z_POWOD);
        rules.add(RegexBase.Z_DATA);
        rules.add(RegexBase.Z_KWOTA);
        rules.add(RegexBase.Z_TYP);
        
        rules.add(RegexBase.HISTORIA_RACHUNKU);
    }


    public void setStatus(Integer status){
        GenericDao<RequiredDocuments> dao=new GenericDao(RequiredDocuments.class);
        RequiredDocuments doc=(RequiredDocuments) dao.getWhere("fk_client", String.valueOf(clientId)).get(0);
        doc.setBikStatus(status);
        dao.update(doc);
    }
    
    public static String getPath() {
        return path;
    }
    
}
