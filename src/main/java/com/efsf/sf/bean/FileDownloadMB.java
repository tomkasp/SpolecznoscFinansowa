package com.efsf.sf.bean;

import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.downloader.FtpDownloader;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * @author WR1EI1
 */

@ManagedBean
@ViewScoped
public class FileDownloadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value = "#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    private RequiredDocuments requiredDocuments;
    
    private FtpDownloader ftpd=new FtpDownloader();
    
    public FileDownloadMB() {    
    }
    
    @PostConstruct
    private void loadRequiredDocuments() {
        
        if(loginMB.getClient()!=null){
            RequiredDocumentsDAO requiredDocumentsDAO = new RequiredDocumentsDAO();
            requiredDocuments = requiredDocumentsDAO.readForFkClient( loginMB.getClient().getIdClient() );
        }
        else{
            RequiredDocumentsDAO requiredDocumentsDAO = new RequiredDocumentsDAO();
            requiredDocuments = requiredDocumentsDAO.readForFkClient( clientCaseMB.getSelectedClientCase().getClient().getIdClient() );
        }
        if(requiredDocuments==null){
            requiredDocuments=new RequiredDocuments();
        }       
    }
    
    public void load1() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getIdCard() ); 
    }
    
    public void load2() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getIncomeStatement() ); 
    }
   
    public void load3() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getDeathCertificate() ); 
    }
    
    public void load4() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getMariageSettlement() ); 
    }
    
    public void load5() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getDivorceAct() ); 
    }
    
    public void load6() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getSeparationAct() ); 
    }
    
    public void load7() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getTitleDeed() ); 
    }
    
    public void load8() throws IOException{
        ftpd.downLoad("rice/SF/USERS/"+loginMB.getIdUser()+"/", requiredDocuments.getBik() ); 
    }
     
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
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

}
