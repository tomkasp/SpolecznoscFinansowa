package com.efsf.sf.bean.client;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.AddressDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.EducationDAO;
import com.efsf.sf.sql.dao.IncomeBusinessActivityDAO;
import com.efsf.sf.sql.dao.IncomeDAO;
import com.efsf.sf.sql.dao.MaritalStatusDAO;
import com.efsf.sf.sql.dao.RegionDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Security;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.joda.time.DateTime;

/**
 * @author WR1EI1
 */
@ManagedBean
@ViewScoped
public class ClientSettingsMB implements Serializable {

    private static final long serialVersionUID = 1L;
    //Update Settings
    @ManagedProperty(value = "#{loginMB.client.idClient}")
    private Integer idClient;
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    @ManagedProperty(value = "#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    private Client client;
    private Integer idMainRegion;
    private Address mainAddress = new Address();
    private Integer idMartialStatus;
    private Integer idEducation;
    ClientDAO clientDAO;
    private ArrayList<IncomeData> incomeTable = new ArrayList<>();
    //DIALOG 1
    private int incomeId;
    private int branchId;
    private boolean isIncome = true;
    private Date currentDate = new DateTime().toDate();
    private Income income = new Income();
    private IncomeBusinessActivity business = new IncomeBusinessActivity();
    private Set<Income> incomeSet = new HashSet<>();
    private Set<IncomeBusinessActivity> businessSet = new HashSet<>();
    private boolean tableEmpty = true;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
    private int idCounter = 0;
    private String email;

    public ClientSettingsMB() {
        clientDAO = new ClientDAO();
    }

    @PostConstruct
    private void loadClient() {

        client = clientDAO.readClientForSettings(idClient);

        idMartialStatus = client.getMaritalStatus().getIdMaritalStatus();
        idEducation = client.getEducation().getIdEducation();

        Iterator<Address> it = client.getAddresses().iterator();
        while (it.hasNext()) {
            mainAddress = it.next();
            idMainRegion = mainAddress.getRegion().getIdRegion();
        }

        Iterator<Income> it2 = client.getIncomes().iterator();
        while (it2.hasNext()) {
            Income i = it2.next();

            IncomeData incomeData = new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getMonthlyNetto().longValue());
            incomeData.setIdIncome(i.getIdIncome());
            incomeData.setIsIncome(true);

            incomeTable.add(incomeData);
            //i.setClient(client);
            incomeSet.add(i);


        }
        //incomeSet= client.getIncomes();

        Iterator<IncomeBusinessActivity> it3 = client.getIncomeBusinessActivities().iterator();
        while (it3.hasNext()) {
            IncomeBusinessActivity iba = it3.next();

            IncomeData incomeData = new IncomeData(iba.getEmploymentType().getName(), iba.getBranch().getName(), iba.getIncomeLastYearNetto().longValue());
            incomeData.setIdIncome(iba.getIdIncomeBusinessActivity());
            incomeData.setIsIncome(false);

            incomeTable.add(incomeData);
            //iba.setClient(client);
            businessSet.add(iba);

        }
        //businessSet= client.getIncomeBusinessActivities();
        email = client.getUser().getEmail();

    }

    public String updateSettings() {

        MaritalStatusDAO msdao = new MaritalStatusDAO();
        MaritalStatus newMaritalStatus = msdao.getMaritalStatus(idMartialStatus);

        EducationDAO edao = new EducationDAO();
        Education newEducation = edao.getEducation(idEducation);

        client.setMaritalStatus(newMaritalStatus);
        client.setEducation(newEducation);

        //INCOME
        IncomeDAO idao = new IncomeDAO();
        Iterator<Income> IncomeIT = client.getIncomes().iterator();
        while (IncomeIT.hasNext()) {
            Iterator<Income> incomeSetIT = incomeSet.iterator();
            boolean isExist = false;

            Income i = IncomeIT.next();

            while (incomeSetIT.hasNext()) {

                Income i2 = incomeSetIT.next();
                System.out.println("ID1 " + i.getIdIncome());
                System.out.println("ID2 " + i2.getIdIncome());
                if (i.getIdIncome() == i2.getIdIncome()) {
                    isExist = true;
                }

            }

            if (!isExist) {
                idao.delete(i);
                System.out.println("USUNIETO!!!! ");
            }

        }

        Iterator<Income> incomeSetIT = incomeSet.iterator();
        while (incomeSetIT.hasNext()) {
            Income i2 = incomeSetIT.next();
            if (i2.getIdIncome() < 0) {
                i2.setIdIncome(null);
            }

        }
        client.setIncomes(incomeSet);

        //INCOME BUSINESS ACTIVITY:
        IncomeBusinessActivityDAO ibadao = new IncomeBusinessActivityDAO();
        Iterator<IncomeBusinessActivity> it2 = client.getIncomeBusinessActivities().iterator();
        while (it2.hasNext()) {

            IncomeBusinessActivity iba = it2.next();
            ibadao.delete(iba);

        }

        Iterator<IncomeBusinessActivity> incomeBASetIT = businessSet.iterator();
        while (incomeBASetIT.hasNext()) {
            IncomeBusinessActivity i2 = incomeBASetIT.next();
            if (i2.getIdIncomeBusinessActivity() < 0) {
                i2.setIdIncomeBusinessActivity(null);
            }

        }

        client.setIncomeBusinessActivities(businessSet);

        clientDAO.update(client);

        UserDAO udao = new UserDAO();
        udao.update(client.getUser());

        RegionDAO rdao = new RegionDAO();
        Region mainRegion = rdao.getRegion(idMainRegion);
        mainAddress.setRegion(mainRegion);
        AddressDAO adao = new AddressDAO();
        adao.update(mainAddress);

        loginMB.setClient(client);

        return "/client/clientSettings?faces-redirect=true";
    }

    public String updatePassword() {

        UserDAO udao = new UserDAO();
        User user = client.getUser();
        user.setPassword(Security.sha1(newPassword));
        udao.update(user);

        return "/client/clientMainPage?faces-redirect=true";
    }

    public void validateCurrentPassword(FacesContext context, UIComponent toValidate, Object value) {
        String password = (String) value;
        password = Security.sha1(password);
        if (!password.equals(client.getUser().getPassword())) {
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

    public void validateEmail(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {

        UserDAO udao = new UserDAO();
        Boolean ifEmailExist = udao.ifEmailExist(value.toString());


        if (value.toString().equals(email)) {
            ifEmailExist = false;
        }

        if (ifEmailExist) {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            FacesMessage msg = new FacesMessage(bundle.getString("duplicateEmails"), bundle.getString("duplicateEmails"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }


    }

    public void deleteIncome(int idIncome, boolean isIncome) {


        Iterator<IncomeData> it = incomeTable.iterator();
        while (it.hasNext()) {
            IncomeData incomeData = it.next();
            if (incomeData.isIsIncome() == isIncome) {

                if (incomeData.getIdIncome() == idIncome) {
                    it.remove();
                }

                if (isIncome == true) {
                    Iterator<Income> incomeIterator = incomeSet.iterator();

                    while (incomeIterator.hasNext()) {

                        Income i = incomeIterator.next();
                        if (i.getIdIncome() == idIncome) {
                            incomeIterator.remove();
                        }

                    }

                } else {
                    Iterator<IncomeBusinessActivity> businessIterator = businessSet.iterator();

                    while (businessIterator.hasNext()) {
                        IncomeBusinessActivity iba = businessIterator.next();
                        if (iba.getIdIncomeBusinessActivity() == idIncome) {
                            businessIterator.remove();
                            System.out.println("USUNIETO 3 !");
                        }
                    }

                }





            }

            System.out.println(incomeTable.size());

        }

    }

    public void toIncome() {
        setIsIncome(true);

    }

    public void toBusinessActivity() {
        setIsIncome(false);

    }

    public void cleanDialog() {

        income = new Income();
        branchId = 0;
        incomeId = 0;
    }

    public void addIncome() {
        idCounter = idCounter - 1;
        System.out.println("COUNTER: " + idCounter);
        EmploymentType et = null;
        for (EmploymentType i : dictionaryMB.getIncome()) {
            if (i.getIdEmploymentType() == incomeId) {
                et = i;
                break;
            }
        }

        Branch b = null;

        for (Branch i : dictionaryMB.getBranch()) {
            if (i.getIdBranch() == branchId) {
                b = i;
                break;
            }
        }
        if (isIncome) {
            income.setIdIncome(idCounter);

            income.setBranch(b);
            income.setEmploymentType(et);
            income.setClient(loginMB.getClient());
            tableEmpty = false;
            IncomeData incomeData = new IncomeData(et.getName(), b.getName(), income.getMonthlyNetto().doubleValue());
            incomeData.setIdIncome(income.getIdIncome());
            incomeData.setIsIncome(true);
            incomeTable.add(incomeData);
            //incomeTable.add(new IncomeData(et.getName(), b.getName(), income.getMonthlyNetto().doubleValue()));
            incomeSet.add(income);
            income = new Income();
        } else {

            for (EmploymentType i : dictionaryMB.getBusinessActivity()) {
                if (i.getIdEmploymentType() == incomeId) {
                    et = i;
                    break;
                }
            }
            business.setIdIncomeBusinessActivity(idCounter);

            business.setBranch(b);
            business.setEmploymentType(et);
            business.setClient(loginMB.getClient());
            tableEmpty = false;

            IncomeData incomeData = new IncomeData(et.getName(), b.getName(), business.getIncomeCurrentYearNetto().doubleValue());
            incomeData.setIdIncome(business.getIdIncomeBusinessActivity());
            incomeData.setIsIncome(false);
            incomeTable.add(incomeData);
            //incomeTable.add(new IncomeData(et.getName(), b.getName(), business.getIncomeCurrentYearNetto().doubleValue()));
            businessSet.add(business);
            business = new IncomeBusinessActivity();
        }
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getIdMainRegion() {
        return idMainRegion;
    }

    public void setIdMainRegion(Integer idMainRegion) {
        this.idMainRegion = idMainRegion;
    }

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Integer getIdMartialStatus() {
        return idMartialStatus;
    }

    public void setIdMartialStatus(Integer idMartialStatus) {
        this.idMartialStatus = idMartialStatus;
    }

    public Integer getIdEducation() {
        return idEducation;
    }

    public void setIdEducation(Integer idEducation) {
        this.idEducation = idEducation;
    }

    public ArrayList<IncomeData> getIncomeTable() {
        return incomeTable;
    }

    public void setIncomeTable(ArrayList<IncomeData> incomeTable) {
        this.incomeTable = incomeTable;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isIsIncome() {
        return isIncome;
    }

    public void setIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public IncomeBusinessActivity getBusiness() {
        return business;
    }

    public void setBusiness(IncomeBusinessActivity business) {
        this.business = business;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public DictionaryMB getDictionaryMB() {
        return dictionaryMB;
    }

    public void setDictionaryMB(DictionaryMB dictionaryMB) {
        this.dictionaryMB = dictionaryMB;
    }

    public Set<Income> getIncomeSet() {
        return incomeSet;
    }

    public void setIncomeSet(Set<Income> incomeSet) {
        this.incomeSet = incomeSet;
    }

    public Set<IncomeBusinessActivity> getBusinessSet() {
        return businessSet;
    }

    public void setBusinessSet(Set<IncomeBusinessActivity> businessSet) {
        this.businessSet = businessSet;
    }

    public boolean isTableEmpty() {
        return tableEmpty;
    }

    public void setTableEmpty(boolean tableEmpty) {
        this.tableEmpty = tableEmpty;
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
}