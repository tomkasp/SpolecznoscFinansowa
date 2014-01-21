package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Security;
import com.efsf.sf.util.Settings;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
@ViewScoped
public class ConsultantSettingsMB implements Serializable {

    private static final long serialVersionUID = 1L;
    //Update Settings

    @ManagedProperty(value = "#{loginMB.consultant.idConsultant}")
    private Integer idConsultant;

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    @ManagedProperty(value = "#{loginMB.user.idUser}")
    private Integer idUser;

    private Consultant consultant;

    private Integer idWorkingPlace;
    private List<Integer> idSelectedBankList = new ArrayList<>();
    private List<Integer> idSelectedInstitutionList = new ArrayList<>();
    private List<Integer> idProductTypes = new ArrayList<>();

    private Integer idRegion;
    private Integer idMainRegion;
    private Integer idInvoiceRegion;
    private Address mainAddress = new Address();
    private Address invoiceAddress = new Address();
    private InvoiceData invoiceData = new InvoiceData();

    private String idSubscriptionType;
    private Subscription subscription;

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    private boolean invoiceFlag = false;

    private static final String PATH = Settings.FTP_PATH;

    @PostConstruct
    private void loadConsultant() {
        ConsultantDAO cdao = new ConsultantDAO();
        consultant = cdao.readConsultantForSettings(idConsultant);
        idWorkingPlace = consultant.getWorkingPlace().getIdWorkingPlace();

        Iterator<Institution> it = consultant.getInstitutions().iterator();
        while (it.hasNext()) {
            Institution i = it.next();
            if (i.getType() == 0) {
                idSelectedBankList.add(i.getIdInstitution());
            }
            if (i.getType() == 1) {
                idSelectedInstitutionList.add(i.getIdInstitution());
            }
        }

        Iterator<ProductType> it2 = consultant.getProductTypes().iterator();
        while (it2.hasNext()) {
            ProductType pt = it2.next();
            idProductTypes.add(pt.getIdProductType());
        }

        idWorkingPlace = consultant.getWorkingPlace().getIdWorkingPlace();

        idRegion = consultant.getRegion().getIdRegion();

        InvoiceDataDAO iddao = new InvoiceDataDAO();

        Iterator<Address> it3 = consultant.getAddresses().iterator();

        while (it3.hasNext()) {
            Address a = it3.next();

            if (a.getType() == 1) {
                mainAddress = a;
                idMainRegion = mainAddress.getRegion().getIdRegion();

            }
            if (a.getType() == 2) {
                invoiceAddress = a;
                idInvoiceRegion = invoiceAddress.getRegion().getIdRegion();

                //I PRZY OKAZJI:
                invoiceData = iddao.loadFromFkAddress(a.getIdAddress());
            }

        }

        Iterator<Subscription> it4 = consultant.getSubscriptions().iterator();
        while (it4.hasNext()) {
            Subscription s = it4.next();
            //ZAWSZE ZWRACA OSTATNI ABONAMENT
            if (!it4.hasNext()) {
                idSubscriptionType = s.getSessionId();
                subscription = s;
            }
        }

        invoiceFlag = consultant.isInvoice();
    }

    public String updateSettings() {

        DictionaryMB dictionaryMB = new DictionaryMB();
        InstitutionDAO idao = new InstitutionDAO();
        ProductTypeDAO ptdao = new ProductTypeDAO();
        RegionDAO rdao = new RegionDAO();

        WorkingPlace wp = dictionaryMB.getWorkingPlace().get(idWorkingPlace - 1);
        consultant.setWorkingPlace(wp);
        //HERE:

        //ADD BANKS
        Set<Institution> institutionSet = new HashSet<>();
        Iterator it = idSelectedBankList.iterator();
        while (it.hasNext()) {
            Integer id = Integer.valueOf(it.next().toString());
            Institution inst = idao.getInstitution(id);
            institutionSet.add(inst);
        }

        //ADD INSTITUTIONS
        it = idSelectedInstitutionList.iterator();
        while (it.hasNext()) {
            Integer id = Integer.valueOf(it.next().toString());
            Institution inst = idao.getInstitution(id);
            institutionSet.add(inst);
        }

        //ADD ALL INSTITUTIONS IN CONSULTANT
        consultant.setInstitutions(institutionSet);

        //ADD PRODUCT TYPES
        Set<ProductType> productTypeSet = new HashSet<>();
        it = idProductTypes.iterator();
        while (it.hasNext()) {
            Integer id = Integer.valueOf(it.next().toString());
            ProductType pt = ptdao.getProductType(id);
            productTypeSet.add(pt);
        }
        consultant.setProductTypes(productTypeSet);

        //ADD CONSULTANT REGION
        Region r = rdao.getRegion(idRegion);
        consultant.setRegion(r);

        consultant.setInvoice(invoiceFlag);

        Set<Address> addressSet = new HashSet<>();

        Region mr = rdao.getRegion(idMainRegion);
        mainAddress.setRegion(mr);
        addressSet.add(mainAddress);

        if (idInvoiceRegion == null) {
            idInvoiceRegion = 0;
        }
        Region ir = rdao.getRegion(idInvoiceRegion);
        invoiceAddress.setRegion(ir);
        addressSet.add(invoiceAddress);

        consultant.setAddresses(addressSet);

        AddressDAO adao = new AddressDAO();
        if (mainAddress.getIdAddress() == null) {
            mainAddress.setConsultant(consultant);
            mainAddress.setType(1);
            adao.save(mainAddress);
        } else {
            adao.update(mainAddress);
        }

        invoiceAddress.setInvoiceDatas(new HashSet<InvoiceData>());
        invoiceAddress.getInvoiceDatas().add(invoiceData);
        invoiceData.setAddress(invoiceAddress);
        
        InvoiceDataDAO iddao = new InvoiceDataDAO();

        if (invoiceAddress.getIdAddress() == null) {
            invoiceAddress.setConsultant(consultant);
            invoiceAddress.setType(2);
            System.out.println("addresss invoice: ============== " + invoiceAddress.getInvoiceDatas());
            adao.save(invoiceAddress);
        } else {
            adao.update(invoiceAddress);
        }
        System.out.println("================================" + invoiceAddress.getIdAddress() +"; ");
        
        if (invoiceData.getIdInvoieData() == null) {
          //  invoiceData.setAddress(invoiceAddress);
            iddao.save(invoiceData);
        } else {
          //  invoiceData.setAddress(invoiceAddress);
            iddao.update(invoiceData);
        }

        //UPDATE CONSULTANT
        ConsultantDAO cdao = new ConsultantDAO();
        //cdao.update(consultant);
        cdao.merge(consultant);
        //UPDATE USER
        loginMB.setConsultant(consultant);

        return "/consultant/consultantSettings?faces-redirect=true";
    }

    public String updatePassword() {

        UserDAO udao = new UserDAO();
        User user = consultant.getUser();
        user.setPassword(Security.sha1(newPassword));
        udao.update(user);

        return "/consultant/consultantMainPage?faces-redirect=true";
    }

    public void validateCurrentPassword(FacesContext context, UIComponent toValidate, Object value) {
        String password = (String) value;
        password = Security.sha1(password);
        if (!password.equals(consultant.getUser().getPassword())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Obecne hasło jest niewłaściwe!", "Obecne hasło jest niewłaściwe!");
            throw new ValidatorException(message);
        }
    }

    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) {
        String password = (String) value;
        if (!password.equals(newPassword)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
            throw new ValidatorException(message);
        }
    }

    public void showAgreementPDF() throws IOException {

//        String sourceLocalPath = "/home/sf/agreement.pdf";
//        String destinationLocalPath = "\\";
//        String ftpPath = PATH + idUser + "/";
//        String fileName = "agreement_consultant_"+idConsultant+".pdf";
//        //CREATE NEW AGREEMENT:
//        AgreementPDFItext itext = new AgreementPDFItext();
//        
//        itext.fillPDF( idConsultant, sourceLocalPath , destinationLocalPath + fileName );
//        
//        //UPLOAD ON FTP:
//       
//        FileUploaderFTP fuftp=new FileUploaderFTP();
//        fuftp.makeDirectory(ftpPath);
//        fuftp.copyFileOnFTP( destinationLocalPath + fileName , ftpPath + fileName );
//        
//        
//        //DOWNLOAD FROM FTP:
//        FtpDownloader ftpd=new FtpDownloader();
//        ftpd.download(ftpPath, fileName ); 
//        
//        //DELETE LOCAL FILE
//        File f = new File(destinationLocalPath+fileName);
//        f.delete();
    }

    public void copyAddress() {
        idInvoiceRegion = idMainRegion;
        invoiceAddress.setCity(mainAddress.getCity());
        invoiceAddress.setZipCode(mainAddress.getZipCode());
        invoiceAddress.setPhone(mainAddress.getPhone());
        invoiceAddress.setStreet(mainAddress.getStreet());
        invoiceAddress.setHouseNumber(mainAddress.getHouseNumber());
    }

    public Integer getIdConsultant() {
        return idConsultant;
    }

    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Integer getIdWorkingPlace() {
        return idWorkingPlace;
    }

    public void setIdWorkingPlace(Integer idWorkingPlace) {
        this.idWorkingPlace = idWorkingPlace;
    }

    public List<Integer> getIdSelectedBankList() {
        return idSelectedBankList;
    }

    public void setIdSelectedBankList(List<Integer> idSelectedBankList) {
        this.idSelectedBankList = idSelectedBankList;
    }

    public List<Integer> getIdSelectedInstitutionList() {
        return idSelectedInstitutionList;
    }

    public void setIdSelectedInstitutionList(List<Integer> idSelectedInstitutionList) {
        this.idSelectedInstitutionList = idSelectedInstitutionList;
    }

    public List<Integer> getIdProductTypes() {
        return idProductTypes;
    }

    public void setIdProductTypes(List<Integer> idProductTypes) {
        this.idProductTypes = idProductTypes;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public Integer getIdMainRegion() {
        return idMainRegion;
    }

    public void setIdMainRegion(Integer idMainRegion) {
        this.idMainRegion = idMainRegion;
    }

    public Integer getIdInvoiceRegion() {
        return idInvoiceRegion;
    }

    public void setIdInvoiceRegion(Integer idInvoiceRegion) {
        this.idInvoiceRegion = idInvoiceRegion;
    }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }

    public String getIdSubscriptionType() {
        return idSubscriptionType;
    }

    public void setIdSubscriptionType(String idSubscriptionType) {
        this.idSubscriptionType = idSubscriptionType;
    }

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Address invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public static String getPATH() {
        return PATH;
    }

    public boolean getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(boolean isInvoice) {
        this.invoiceFlag = isInvoice;
    }

}
