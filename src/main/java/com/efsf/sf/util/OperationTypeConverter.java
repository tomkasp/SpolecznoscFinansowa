/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.efsf.sf.util;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.OperationType;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
/**
 *
 * @author Marcin
 */
public class OperationTypeConverter implements Converter {
    
    private static GenericDao<OperationType> genDao = new GenericDao(OperationType.class);

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return genDao.getWhere("operationName", string).get(0); 
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((OperationType) o).getOperationName(); //To change body of generated methods, choose Tools | Templates.
    }
}
