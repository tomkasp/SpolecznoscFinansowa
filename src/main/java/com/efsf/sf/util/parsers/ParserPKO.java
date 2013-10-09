package com.efsf.sf.util.parsers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// format pliku csv: "Data operacji","Data waluty","Typ transakcji","Kwota","Waluta","Saldo po transakcji","Opis transakcji"
public class ParserPKO {

    public static void main(String[] args) {
        ParserPKO obj = new ParserPKO();
        obj.run();

    }

    public void run() {
       // int i = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader("C:\\WBK6.csv"), ',','"',2);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
              //  if (i >= 31) {
                    // nextLine[] is an array of values from the line

                    //System.out.println(reader.readNext().length);
                    System.out.println(nextLine[0]+ " | " + nextLine[1]+ " | " + nextLine[2]+ " | " + nextLine[3]+ " | " + nextLine[4]+ " | " + nextLine[5] + " | " + nextLine[6]+ " | " + nextLine[7]+ " | " + nextLine[8]+ " | " + nextLine[9]+ " | " + nextLine[10]+ " | " + nextLine[11]+ " | " + nextLine[12]+ " | " + nextLine[13]+ " | " + nextLine[14]);
                    
              //  }
              //  i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ParserPKO.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
