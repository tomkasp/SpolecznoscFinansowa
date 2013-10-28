package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.AddressDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

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
    
    
    private String getReportImagePath() throws MalformedURLException{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            URL url = new URL(request.getRequestURL().toString());
            return url.getProtocol()+"://"+url.getHost()+":"+url.getPort()+"/"+request.getContextPath()
                +"/reports/";
    }

    public void generateInvoice(Integer idSubscription, String sessionId) throws JRException, IOException{
        GenericDao<SubscriptionType> dao=new GenericDao(SubscriptionType.class);
        GenericDao<Subscription> dao2=new GenericDao(Subscription.class);
        
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("consultant", loginMB.getConsultant());
        params.put("address", getInvoiceAddress());
        params.put("sub", dao.getById(idSubscription));
        
        Date date=dao2.getById(sessionId).getTransactionDate();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        params.put("number", dao2.getById(sessionId).getTransactionNumber()+"/"+cal.get(Calendar.YEAR));
        params.put("date", date.toString());
        export("invoice.jrxml", params, new JREmptyDataSource());
    }
    
   public void generateReceipt() throws JRException, IOException
   {
        GenericDao<SubscriptionType> dao=new GenericDao(SubscriptionType.class);
        Map<String, Object> params=new HashMap<>();
        params.put("Consultant", loginMB.getConsultant());
        params.put("Address", new AddressDAO().loadMainAddressFromFkConsultant(loginMB.getConsultant().getIdConsultant()));
        params.put("Sub", dao.getById(1));
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

        if (params != null) {
            String reportsDirPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/");
            File reportsDir = new File(reportsDirPath);
            params.put(JRParameter.REPORT_FILE_RESOLVER, new SimpleFileResolver(reportsDir));
        }
        
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
