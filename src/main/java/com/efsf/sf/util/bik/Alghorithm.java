package com.efsf.sf.util.bik;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Bik;
import com.efsf.sf.sql.entity.BikHistoriaRachunku;
import com.efsf.sf.sql.entity.BikRachunek;
import com.efsf.sf.sql.entity.BikZapytanie;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.RequiredDocuments;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    
    private List<Table> tables= new ArrayList<Table>();
    private Map<String, Object> result = new HashMap<String, Object>();
    private Integer clientId; 
    
    private String bikSample="Biuro Informacji Kredytowej S.A.";
    
    public Alghorithm(String file, String password, Integer clientId) {
        setUpRules();
        this.file=file;
        this.password=password;
        this.clientId=clientId;
    }

//    public void doOCRandSave(String file, String password) throws Exception {
//        PDFWithImages pdf = new PDFWithImages();
//        String text = pdf.getTextFromPDF("C:/bik/" + file, password);
//        PrintWriter out = new PrintWriter("C:/bik/" + file + ".txt");
//        out.print(text);
//        out.close();
//    }

    
    @Override
    public void run(){
        try {
            PDFWithText pdf = new PDFWithText();
            pdf.reset();
            String text = pdf.getTextFromPDF("/home/sf/bik/" + file, password);
            
            //Poprawny BIK
            if(text.length()>1000 && text.contains(bikSample)){
            
                PrintWriter out = new PrintWriter("C:/bik/texty/" + file + ".txt");
                out.print(text);
                out.close();

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
            File f=new File("/home/sf/bik/"+file);
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
               List<String> ls=new ArrayList<String>(); 
               while(m.find())
                   ls.add(trim(m.group("val")));
               
               if(!ls.isEmpty()) getResult().put(rules.get(i).getName(), ls);
               
            } else if (rules.get(i).getType() == RuleBase.T_ONE) {
                
                if (m.find())
                    getResult().put(rules.get(i).getName(), trim(m.group("val")));
                
            } else if (rules.get(i).getType() == RuleBase.T_ONE_TABLE) {
                
                if (m.find())
                    getResult().put(rules.get(i).getName(), TableHelper.getAsTable(m.group("val")));
                
            } else if (rules.get(i).getType() == RuleBase.T_MANY_TABLE) {
                 
                 while(m.find()) 
                     getResult().put(rules.get(i).getName(), TableHelper.getAsTable(m.group("val"))); 
              
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
                   .replaceAll("(?m)^[ \\t;]*\\r?\\n", "");
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
        bik.setKlasa((String) result.get(RegexBase.KLASYFIKACJA_BIK.getName()));
        bik.setOcenaPunktowa((String) result.get(RegexBase.OCENA_PUNKTOWA.getName()));
        bik.setPesel((String) result.get(RegexBase.PESEL.getName()));
        bik.setRzZaleglosc030dni((String) result.get(RegexBase.RACH_ZAMK_ZALEG_0_30.getName()));
        bik.setRzUmorzone((String) result.get(RegexBase.RACH_ZAMK_UMORZONY.getName()));
        bik.setRzOdzyskane((String) result.get(RegexBase.RACH_ZAMK_ODZYSKANY.getName()));
        bik.setRoZaleglosc030dni((String) result.get(RegexBase.RACH_OTW_ZALEG_0_30.getName()));
        bik.setRoZaleglosc3190dni((String) result.get(RegexBase.RACH_OTW_ZALEG_31_90.getName()));
        bik.setRoZaleglosc91180dni((String) result.get(RegexBase.RACH_OTW_ZALEG_91_180.getName()));
        bik.setRoZaleglosc180dni((String) result.get(RegexBase.RACH_OTW_ZALEG_180_WIECEJ.getName()));
        bik.setRoEgzekucja((String) result.get(RegexBase.RACH_OTW_EGZEKUCJA.getName()));
        bik.setRoWindykacja((String) result.get(RegexBase.RACH_OTW_WINDYKACJA.getName()));
        bik.setRachunkiKwestionowane((String) result.get(RegexBase.RACHUNKI_KWESTIONOWANE.getName()));
        bik.setClient(client);
        
        GenericDao<Bik> dao = new GenericDao(Bik.class);
        dao.save(bik);

        //Rachunki
        GenericDao<BikRachunek> dao2 = new GenericDao(BikRachunek.class);
        ArrayList<String> kwoty = (ArrayList<String>) result.get(RegexBase.R_KWOTA.getName());
        if (kwoty != null) {

            for (int i = 0; i < kwoty.size(); i++) {
             BikRachunek rachunek=new BikRachunek();
             rachunek.setWaluta(((ArrayList<String>) result.get(RegexBase.R_WALUTA.getName())).get(i));
             rachunek.setDataPowstaniaRelacji(((ArrayList<String>) result.get(RegexBase.R_DATA_RELACJI.getName())).get(i));
             rachunek.setTypTransakcji(((ArrayList<String>) result.get(RegexBase.R_TYP_TRANSAKCJI.getName())).get(i));
             rachunek.setRelacjaKlienta(((ArrayList<String>) result.get(RegexBase.R_RELACJA.getName())).get(i));
             rachunek.setKwotaZKosztemOds1(((ArrayList<String>) result.get(RegexBase.R_KWOTA.getName())).get(i));
             rachunek.setBik(bik);
             dao2.save(rachunek);
             
                //Zapisz historie rachunku
                GenericDao<BikHistoriaRachunku> dao3 = new GenericDao(BikHistoriaRachunku.class);
                
                for(int j=0; j<tables.get(i).data.size();j++){
                    BikHistoriaRachunku historia=new BikHistoriaRachunku();
                    historia.setRachunek(rachunek);
                    historia.setNumer(tables.get(i).data.get(j)[0]);
                    historia.setData(tables.get(i).data.get(j)[1]);
                    historia.setStatus(tables.get(i).data.get(j)[2]);
                    historia.setKwotaDoSplaty(tables.get(i).data.get(j)[3]);
                    historia.setSaldoNaleznosci(tables.get(i).data.get(j)[04]);
                    historia.setOpoznienie(tables.get(i).data.get(j)[5]);
                    historia.setZaleglePlatnosci(tables.get(i).data.get(j)[6]);
                    historia.setSaldoLimitu(tables.get(i).data.get(j)[7]);
                    historia.setWaluta(tables.get(i).data.get(j)[8]);
                    dao3.save(historia);
                }
                
                
            }
        }
        
        
        //Zapytania
        GenericDao<BikZapytanie> dao3 = new GenericDao(BikZapytanie.class);
        ArrayList<String> zapytania = (ArrayList<String>) result.get(RegexBase.Z_KWOTA.getName());
        if (zapytania != null) {

            for (int i = 0; i < zapytania.size(); i++) {
             BikZapytanie zapytanie=new BikZapytanie();
             zapytanie.setData(((ArrayList<String>) result.get(RegexBase.Z_DATA.getName())).get(i));
             zapytanie.setKwota(((ArrayList<String>) result.get(RegexBase.Z_KWOTA.getName())).get(i));
             zapytanie.setPowod(((ArrayList<String>) result.get(RegexBase.Z_POWOD.getName())).get(i));
             zapytanie.setTyp(((ArrayList<String>) result.get(RegexBase.Z_TYP.getName())).get(i));
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
        rules.add(RegexBase.OCENA_PUNKTOWA); //

        rules.add(RegexBase.RACH_ZAMK_ZALEG_0_30);
        rules.add(RegexBase.RACH_ZAMK_UMORZONY);
        rules.add(RegexBase.RACH_ZAMK_ODZYSKANY);
        rules.add(RegexBase.RACH_OTW_ZALEG_0_30);
        rules.add(RegexBase.RACH_OTW_ZALEG_31_90);
        rules.add(RegexBase.RACH_OTW_ZALEG_91_180);
        rules.add(RegexBase.RACH_OTW_ZALEG_180_WIECEJ);

        rules.add(RegexBase.RACH_OTW_WINDYKACJA);
        rules.add(RegexBase.RACH_OTW_EGZEKUCJA);
        rules.add(RegexBase.RACHUNKI_KWESTIONOWANE); //

        //Rachunki
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
    
}
