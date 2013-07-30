/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.RegionDAO;
import com.efsf.sf.sql.entity.Region;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author admin
 */
@ManagedBean
@ApplicationScoped
public class DictionaryMB {

    /**
     * Creates a new instance of DictionaryMB
     */
    
    private List<Region> wojewodztwa;
    
    
    public DictionaryMB() {
        RegionDAO reg = new RegionDAO();
        wojewodztwa = reg.regionList();
    }

    /**
     * @return the wojewodztwa
     */
    public List<Region> getWojewodztwa() {
        return wojewodztwa;
    }

    /**
     * @param wojewodztwa the wojewodztwa to set
     */
    public void setWojewodztwa(List<Region> wojewodztwa) {
        this.wojewodztwa = wojewodztwa;
    }
}
