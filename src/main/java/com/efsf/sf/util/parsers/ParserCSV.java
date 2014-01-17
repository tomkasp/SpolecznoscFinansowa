package com.efsf.sf.util.parsers;

import au.com.bytecode.opencsv.CSVReader;
import com.efsf.sf.sql.dao.AmountHistoryDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Client;
import static com.efsf.sf.util.Security.md5;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

// format pliku csv: "Data operacji","Data waluty","Typ transakcji","Kwota","Waluta","Saldo po transakcji","Opis transakcji"
public class ParserCSV {

    private final int[][] tab = { 
        {1,11,12,5,4,3}//wbk-> data, kwotaMA,saldo,konto,opis,tytul
         //nie uzywane
};

//    public static void main(String[] args) throws ParseException {
//        ParserCSV pko = new ParserCSV();
//        //pko.run(stream);
//    }

    public void run(InputStream stream, Client client) throws ParseException {
      int i = 0;
        
        AmountHistory amhist = null;
        //AmountHistoryDAO amDAO = new AmountHistoryDAO();
        GenericDao<AmountHistory> genDao = new GenericDao(AmountHistory.class);
        try {
            
            CSVReader reader = new CSVReader(new InputStreamReader(stream,"UTF-8") , ',','"',3);
            //CSVReader reader = new CSVReader(new FileReader("C:\\WBK6.csv"), ',','"',3);
            //input stream;
            
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (i < 1) {
                    // nextLine[] is an array of values from the line
                    amhist = new AmountHistory();

                    String data = nextLine[tab[0][0]];
                    DateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
                    
                    //System.out.println(reader.readNext().length);
                    System.out.println(nextLine[tab[0][0]] + " | " + nextLine[tab[0][1]] + " | " + nextLine[tab[0][2]] + " | " + nextLine[tab[0][3]] + " | " + nextLine[tab[0][4]]);
                    amhist.setOperationDate(formatter.parse(data));
                    amhist.setAmount(new BigDecimal(nextLine[tab[0][1]].replace(",", ".")));
                    amhist.setAfterOperation(new BigDecimal(nextLine[tab[0][2]].replace(",", ".")));
                    amhist.setAccountNumber(nextLine[tab[0][3]]);
                    amhist.setReceiver(nextLine[tab[0][4]]);
                    amhist.setTitle(nextLine[tab[0][5]]);
                    
                    amhist.setHashCode(md5(nextLine[tab[0][0]]+nextLine[tab[0][1]]+nextLine[tab[0][2]]+nextLine[tab[0][3]]+nextLine[tab[0][4]]+nextLine[tab[0][5]]));
                    
                    amhist.setClient(client);
                    
                    
                    genDao.save(amhist);
                    //amDAO.save(amhist);
                    amhist = null;
            }
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
