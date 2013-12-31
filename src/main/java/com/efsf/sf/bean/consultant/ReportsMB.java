package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.AddressDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.SubscriptionDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.IOUtils;

@ManagedBean
@RequestScoped
public class ReportsMB implements Serializable {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public void generateInvoice(Integer idSubscriptionType, String sessionId) throws JRException, IOException {

        if (getInvoiceAddress() == null) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Uzupełnij adres dla faktury w swoim profilu celu wygenerowania dokumentu."));
            return;
        }

        createInvoice(idSubscriptionType, sessionId, getInvoiceAddress(), loginMB.getConsultant(), false);

    }

    public OutputStream createInvoice(Integer idSubscriptionType, String sessionId, Address address, Consultant consultant, boolean asOutputStream) throws IOException, JRException {
        GenericDao<SubscriptionType> dao = new GenericDao(SubscriptionType.class);
        GenericDao<Subscription> dao2 = new GenericDao(Subscription.class);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("consultant", consultant);

        params.put("sub", dao.getById(idSubscriptionType));

        Subscription subs = dao2.getById(sessionId);
        Date date = subs.getTransactionDate();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        params.put("number", subs.getTransactionNumber() + "/" + cal.get(Calendar.YEAR));
        params.put("date", date.toString());

        if (!subs.isPaymentType()) {
            params.put("paymentType", "Zapłacono gotówką:");
            params.put("dateToText", "");
        } else {
            cal.add(Calendar.DATE, 30);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            params.put("paymentType", "Płatność przelewem:");
            params.put("dateToText", "Termin płatności: 30 dni (upływa dnia " + sdf.format(cal.getTime()) + ")");
        }

        params.put("address", address);


        if (asOutputStream) {
            return exportAsStream("invoice.jrxml", params, new JREmptyDataSource());
        } else {
            export("invoice.jrxml", params, new JREmptyDataSource());
            return null;
        }
    }

    private Address getInvoiceAddress() {
        AddressDAO dao = new AddressDAO();
        Address address = dao.loadInvoiceAddressFromFkConsultant(loginMB.getConsultant().getIdConsultant());

        if (address == null) {
            return dao.loadMainAddressFromFkConsultant(loginMB.getConsultant().getIdConsultant());
        } else {
            return address;
        }
    }

    private void export(String template, Map<String, Object> params, JRDataSource dataSource) throws IOException, JRException {
        JasperReport report;
        addFileResolver(params);

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        ctx.setResponseContentType("application/pdf");
        try {
            report = JasperCompileManager.compileReport(ctx.getRealPath("/reports/" + template));
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, ctx.getResponseOutputStream());

        } catch (JRException | IOException e) {
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().responseComplete();
    }

    private OutputStream exportAsStream(String template, Map<String, Object> params, JRDataSource dataSource) throws IOException, JRException {
        JasperReport report;
        addFileResolver(params);
        OutputStream output = new ByteArrayOutputStream();

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        try {
            report = JasperCompileManager.compileReport(ctx.getRealPath("/reports/" + template));
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);

        } catch (JRException e) {
            e.printStackTrace();
        }

        return output;
    }

    private void addFileResolver(Map<String, Object> params) {
        if (params != null) {
            String reportsDirPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/");
            File reportsDir = new File(reportsDirPath);
            params.put(JRParameter.REPORT_FILE_RESOLVER, new SimpleFileResolver(reportsDir));
        }
    }

    public void generateInvoices() throws IOException, JRException, ArchiveException {
        ArrayList<OutputStream> list = new ArrayList<>();
        SubscriptionDAO dao=new SubscriptionDAO();
        List<Subscription> sub=dao.getAllSubscriptionsForInvoices();
        AddressDAO addressDao=new AddressDAO();
        
        for(Subscription s: sub){
            
            Address address=addressDao.loadInvoiceAddressFromFkConsultant(s.getConsultant().getIdConsultant());
            
            if(s.getSubscriptionType()!=null && s.getConsultant()!=null && address!=null && address.complete() && s.getTransactionDate()!=null)
            list.add(createInvoice(s.getSubscriptionType().getIdSubscriptionType(), s.getSessionId(), 
                  address, s.getConsultant(), true));
        }

        getAsZip(list);
    }

    public void getAsZip(ArrayList<OutputStream> streamArray) throws ArchiveException {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ExternalContext etx = ctx.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) etx.getResponse();
            response.setContentType("application/octet-stream");
            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=invoices.zip");
            response.setHeader("Cache-Control", "no-cache");
            ServletOutputStream responseStream = response.getOutputStream();
            
            ArchiveOutputStream os = new ArchiveStreamFactory().createArchiveOutputStream("zip", responseStream);
            Integer i=1;
            
            for (OutputStream japserStream : streamArray) {
                ByteArrayOutputStream jasperStream2 = (ByteArrayOutputStream) japserStream;
                InputStream inputZipStream = new ByteArrayInputStream(jasperStream2.toByteArray());
                os.putArchiveEntry(new ZipArchiveEntry(i.toString()+".pdf"));
                IOUtils.copy(inputZipStream, os);
                os.closeArchiveEntry();
                i++;
            }
            os.close();


            //write to servlet output stream
            responseStream.flush();
            responseStream.close();
            response.flushBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().responseComplete();
    }
}