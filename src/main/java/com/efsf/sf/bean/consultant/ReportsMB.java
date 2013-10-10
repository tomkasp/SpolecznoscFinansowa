package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.AddressDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.entity.Address;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@ManagedBean
@RequestScoped
public class ReportsMB implements Serializable{

    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public void generateInvoice() throws JRException, IOException{
        
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("consultant", loginMB.getConsultant());
        params.put("address", getInvoiceAddress());
        export("invoice.jrxml", params, new JREmptyDataSource());
    }
    
   public void generateReceipt() throws JRException, IOException
   {
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("Consultant", loginMB.getConsultant());
        params.put("Address", getInvoiceAddress());
        export("receipt.jrxml", params, new JREmptyDataSource());
    }
    
    
    
    private Address getInvoiceAddress(){
        AddressDAO dao=new AddressDAO();
        Address address=dao.loadInvoiceAddressFromFkConsultant(loginMB.getConsultant().getIdConsultant());
        
        if(address==null){
            return dao.loadMainAddressFromFkConsultant(loginMB.getConsultant().getIdConsultant());
        } else {
            return address;
        }
    }
    
    private void export(String template, Map<String, Object> params, JRDataSource dataSource) throws IOException, JRException {
        JasperReport report;

        ExternalContext ctx=FacesContext.getCurrentInstance().getExternalContext();
        ctx.setResponseContentType("application/pdf");
        try {
            report = JasperCompileManager.compileReport(ctx.getRealPath("/reports/"+template));        
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, ctx.getResponseOutputStream());
            
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().responseComplete();
    }
    
    
}