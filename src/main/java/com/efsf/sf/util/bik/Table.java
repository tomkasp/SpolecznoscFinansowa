package com.efsf.sf.util.bik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {

    public List<String[]> data = new ArrayList<String[]>();
    public String[] exceptions = {"Nr"};

    public Table() {
        data.add(new String[9]);
    }

    public String[] getLastRow() {
        return data.get(data.size() - 1);
    }

    //remove entries with page footer or without data
    public void postProcess() {
        //rows iteration        
        for (int i = data.size() - 1; i >= 0; i--) {
            //rules iteration
            if (data.get(i)[0] == null) {
                data.remove(i);
                
            } else if(data.get(i)[2]==null && data.get(i)[3]==null && 
                        data.get(i)[4]==null && data.get(i)[5]==null){
                data.remove(i);
            } else {
                for (String exc : exceptions) {
                    if (data.get(i) != null && data.get(i)[0].startsWith(exc)) {
                        data.remove(i);
                    }
                }
           }
        }
    }
}