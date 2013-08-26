package com.efsf.sf.util.analyser;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author admin
 */
public class TestTab {
    private int idProduct;
    private int grade=0;
    
    public TestTab(){}
    
    public TestTab(int idProduct, int grade){
        this.idProduct = idProduct;
        this.grade = grade;
    }

    
//    public void zinkrementuj(int idProduct, ArrayList<TestTab> tab){
//       Iterator i = tab.iterator();
//       while(i.hasNext()){
//           TestTab c = (TestTab)i.next();
//           if(c.idProduct==idProduct){
//               c.setGrade(c.getGrade()+1);
//           }
//       }
//    }
    
    
    public int getIdProduct() {
        return idProduct;
    }

    public int getGrade() {
        return grade;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    
}
