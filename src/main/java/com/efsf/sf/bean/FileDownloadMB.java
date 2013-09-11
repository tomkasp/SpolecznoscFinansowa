package com.efsf.sf.bean;

import com.efsf.sf.bean.client.CaseViewMB;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.ftp.FtpDownloader;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class FileDownloadMB implements Serializable {

    
    @ManagedProperty(value = "#{bikMB}")
    private BikMB bikMB;
    
    private static final long serialVersionUID = 1L;
    
    private static final String PATH =  "rice/SF/USERS/";
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value = "#{caseViewMB}")
    private CaseViewMB caseViewMB;
    
    private RequiredDocuments requiredDocuments;
    
    private FtpDownloader ftpd=new FtpDownloader();
    
    private Integer idUser;
    
    @PostConstruct
    private void loadRequiredDocuments() {
        
        if(loginMB.getClient()!=null){
            RequiredDocumentsDAO requiredDocumentsDAO = new RequiredDocumentsDAO();
            requiredDocuments = requiredDocumentsDAO.readForFkClient( loginMB.getClient().getIdClient() );
            idUser=loginMB.getIdUser();
        }
        else{
            RequiredDocumentsDAO requiredDocumentsDAO = new RequiredDocumentsDAO();
            requiredDocuments = requiredDocumentsDAO.readForFkClient( caseViewMB.getSelectedClientCase().getClient().getIdClient() );
            idUser=caseViewMB.getSelectedClientCase().getClient().getUser().getIdUser();
            
        }
        if(requiredDocuments==null){
            requiredDocuments=new RequiredDocuments();
        }    
        
    }
    
    public void parseBik(Integer clientId) throws Exception{
        requiredDocuments.setBikStatus(1);
        bikMB.parseBik(clientId);
    }
    
    public void load1() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getIdCard() ); 
    }
    
    public void load2() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getIncomeStatement() ); 
    }
   
    public void load3() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getDeathCertificate() ); 
    }
    
    public void load4() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getMariageSettlement() ); 
    }
    
    public void load5() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getDivorceAct() ); 
    }
    
    public void load6() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getSeparationAct() ); 
    }
    
    public void load7() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getTitleDeed() ); 
    }
    
    public void load8() throws IOException{
        ftpd.download(PATH+idUser+"/", requiredDocuments.getBik() ); 
    }
    
    public void loadInstitutionFile(Integer idInstitution, String file) throws IOException
    {
        ftpd.download(PATH+"inst" + idInstitution + "/", file); 
    }
     
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public RequiredDocuments getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(RequiredDocuments requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public CaseViewMB getCaseViewMB() {
        return caseViewMB;
    }

    public void setCaseViewMB(CaseViewMB caseViewMB) {
        this.caseViewMB = caseViewMB;
    }

    /**
     * @return the bikMB
     */
    public BikMB getBikMB() {
        return bikMB;
    }

    /**
     * @param bikMB the bikMB to set
     */
    public void setBikMB(BikMB bikMB) {
        this.bikMB = bikMB;
    }

}
