package com.efsf.sf.util.bik;

import java.util.ArrayList;


public class TableHelper {

public static final int MIN_TABLE_CELLS=2;    
    
    public static ArrayList<String[]> getAsTable(String text){
        String[] rows=text.split("\\n");
        ArrayList<String[]> table=new ArrayList<String[]>();
        
        for(String r: rows){
           String[] delimited_row=r.split(";");
           if(delimited_row.length>=MIN_TABLE_CELLS){
               table.add(delimited_row);
           }
        }
        
        return table;
        
    }    
    
}
