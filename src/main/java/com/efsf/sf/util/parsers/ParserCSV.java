package com.efsf.sf.util.parsers;

import au.com.bytecode.opencsv.CSVReader;
import com.efsf.sf.sql.dao.AmountHistoryDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.OperationType;
import static com.efsf.sf.util.Security.md5;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

// format pliku csv: "Data operacji","Data waluty","Typ transakcji","Kwota","Waluta","Saldo po transakcji","Opis transakcji"
public class ParserCSV {

    private final int[][] tab = {
        {1, 10, 11, 12, 5, 4, 3}//wbk-> data, KwotaWN, kwotaMA,saldo,konto,opis,tytul
    //nie uzywane
    };

//    public static void main(String[] args) throws ParseException {
//        ParserCSV pko = new ParserCSV();
//        //pko.run(stream);
//    }
    public void run(InputStream stream, Client client) throws ParseException {
        int i = 0;
        List<AmountHistory> list = new ArrayList<>();
        AmountHistory amhist = null;
        AmountHistoryDAO amDAO = new AmountHistoryDAO();
        GenericDao<AmountHistory> genDao = new GenericDao(AmountHistory.class);
        try {

            CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"), ',', '"', 3);
            //CSVReader reader = new CSVReader(new FileReader("C:\\WBK6.csv"), ',','"',3);
            //input stream;

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                amhist = new AmountHistory();

                String data = nextLine[tab[0][0]];
                DateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");

                    //System.out.println(reader.readNext().length);
                if (!nextLine[tab[0][1]].equals("") && nextLine[tab[0][2]].equals("")) {
                    amhist.setAmount(new BigDecimal("-" + nextLine[tab[0][1]].replace(",", ".")));
                }
                if (nextLine[tab[0][1]].equals("") && !nextLine[tab[0][2]].equals("")) {
                    amhist.setAmount(new BigDecimal(nextLine[tab[0][2]].replace(",", ".")));
                }

                //System.out.println(nextLine[tab[0][0]] + " | " + nextLine[tab[0][1]] + " | " + nextLine[tab[0][2]] + " | " + nextLine[tab[0][3]] + " | " + nextLine[tab[0][4]]);
                amhist.setOperationDate(formatter.parse(data));

                amhist.setAfterOperation(new BigDecimal(nextLine[tab[0][3]].replace(",", ".")));
                amhist.setAccountNumber(nextLine[tab[0][4]]);
                amhist.setReceiver(nextLine[tab[0][5]]);
                amhist.setTitle(nextLine[tab[0][6]]);

                amhist.setHashCode(md5(nextLine[tab[0][0]]
                        + nextLine[tab[0][1]] + nextLine[tab[0][2]]
                        + nextLine[tab[0][3]] + nextLine[tab[0][4]]
                        + nextLine[tab[0][5]] + nextLine[tab[0][6]]));

                amhist.setClient(client);
                if(!amDAO.checkMD5(amhist.getHashCode(), client.getIdClient())){
                    list.add(amhist);
                }
                
                

                    //amDAO.save(amhist);
            }

            list = autoAnalise(list);

            for (AmountHistory a : list) {
                genDao.save(a);
            }

        } catch (IOException ex) {
            Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
       //podziel według miesiąca i roku 
       public Map<String, List<AmountHistory>> splitByDate(List<AmountHistory> list){
           Map<String, List<AmountHistory>> splitByDate=new HashMap<>();           
           for(AmountHistory h: list){
               String date=h.getOperationDate().getMonth()+" "+h.getOperationDate().getYear();
               if(splitByDate.get(date)!=null){
                   splitByDate.get(date).add(h);
               } else {
                   List<AmountHistory> innerList=new ArrayList<>();
                   innerList.add(h);
                   splitByDate.put(date, innerList);
               }
           }
           
           
           return splitByDate;
      } 

 
       public List<AmountHistory> autoAnalise(List<AmountHistory> list){
           List<AmountHistory> result=new ArrayList<>();
           
           GenericDao<OperationType> typeDao=new GenericDao(OperationType.class);
           Map<String, List<AmountHistory>> splitByDate=splitByDate(list);
           
           for(Entry<String, List<AmountHistory>> entry: splitByDate.entrySet()){
               AmountHistory max=entry.getValue().get(0);
               AmountHistory min=entry.getValue().get(0);
                       
               for(AmountHistory a: entry.getValue()){
                   Double amount=a.getAmount().doubleValue();
                   //drobny wydatek
                   if(amount>=-10 && amount<0){
                       a.setOperationType(typeDao.getById(1));
                   //wydatek
                   } else if(amount<-10){    
                       a.setOperationType(typeDao.getById(5));
                   //przych�d jednorazowy    
                   } else if(amount>0){
                       a.setOperationType(typeDao.getById(7));
                   }
                   
                   if(a.getAmount().doubleValue()>max.getAmount().doubleValue()){
                       max=a;
                   } else if(a.getAmount().doubleValue()<min.getAmount().doubleValue()){
                       min=a;
                   }
               }
               //pensja
               if(max.getAmount().doubleValue()>1000){
                   max.setOperationType(typeDao.getById(6));               }
               //kredyt
               if(min.getAmount().doubleValue()<-200){
                   min.setOperationType(typeDao.getById(2));
               }
               result.addAll(entry.getValue());
           }
           return result;
       }
       
}
