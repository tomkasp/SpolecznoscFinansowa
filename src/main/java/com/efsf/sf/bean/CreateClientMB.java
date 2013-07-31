/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.EducationDAO;
import com.efsf.sf.sql.dao.MaritalStatusDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Branch;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBuisnessActivity;
import com.efsf.sf.sql.entity.Region;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


/**
 *
 * @author XaI
 */
@ManagedBean
@SessionScoped
public class CreateClientMB implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value="#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    
    User user; 
    
    //General information
    
    private int loginNumber;
    private String  login;
    private String  name;
    private String  password;
    private String  passwordCheck;
    private String  email; 
    private String surname; 
    private String familyName;
    private Date birthDate;
    private int maritalStatus;
    private int education; 
    private Boolean sex;
    private String pesel;
    private int birthPlace;
    
    //Income
    
    private ArrayList<IncomeData> incomeTable = new ArrayList<IncomeData>();
    private Income income = new Income();
    private IncomeBuisnessActivity business = new IncomeBuisnessActivity();
    
    private HashSet<Income> incomeSet = new HashSet();
    private HashSet<IncomeBuisnessActivity> businessSet = new HashSet();
    
    //Fields used to make select menus working
    
    private int incomeId;
    private int branchId;
    private boolean isIncome = true;
    
    //Address Data
    
    private int regionId;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
    private Address address = new Address();
    private HashSet<Address> addressSet = new HashSet();
    
    public CreateClientMB()
    {
//        UserDAO userDao = new UserDAO();
//        
//        user = new User("", "", "", 3);        
//        userDao.save(user);
//        loginNumber = user.getIdUser();
//        login = Integer.toString(loginNumber);
//        login = ("000000" + login).substring(login.length());
    }
//    
//    @PreDestroy
//    public void deleteUser()
//    {
////        UserDAO userDao = new UserDAO();
////        
////        userDao.delete(user);
////        
////        System.out.println("Usunieto");
//        
//    }
    public String createClientAccount() throws NoSuchAlgorithmException
    {
        UserDAO userDao = new UserDAO();
        ClientDAO clientDao = new ClientDAO();
        EducationDAO eduDao = new EducationDAO();
        MaritalStatusDAO maritalDao = new MaritalStatusDAO();
        
    
        user = new User();
        user.setPassword(Security.sha1(password));
        user.setEmail(email);
        user.setType(3);
        user.setLogin(email); 
        
        Client client = new Client();
        client.setName(name);
        client.setUser(user);
        client.setLastName("");
        client.setEducation(eduDao.getEducation(7));  
        client.setMaritalStatus(maritalDao.getMaritalStatus(7));
        client.setPoints(5);
        
        userDao.save(user);
        clientDao.save(client);
        
        user.setLogin(("000000" + Integer.toString(user.getIdUser())).substring(Integer.toString(user.getIdUser()).length()));
        
        userDao.update(user);
        
        
        loginMB.setUser(user);
        loginMB.setClient(client);
        
        
        
        return "/client/clientFillAccountData?faces-redirect=true";
    }
        
    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) 
    {
        String confirmPassword = (String)value;
        if (!confirmPassword.equals(password)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }
    }
    
    public String redirectToCreateConsultant()
    {
        return "/consultant/consultantCreateAccount?faces-redirect=true";   
    }
    
    public void cleanDialog()
    {
       
        income = new Income();
        branchId = 0;
        incomeId = 0;
        
        System.out.println("Wyczyściło");
    }
    
    public void addIncome()
    {
                EmploymentType et = null; 
                for (EmploymentType i : dictionaryMB.getIncome())
                {
                    if (i.getIdEmploymentType() == incomeId)
                    {
                        et = i;
                        break;
                    }
                }
                
                Branch b = null;
 
                for (Branch i : dictionaryMB.getBranch())
                {
                    if (i.getIdBranch() == branchId)
                    {
                        b = i;
                        break;
                    }
                }
                if (isIncome)
                {
                    income.setBranch(b);
                    income.setEmploymentType(et);
                    income.setClient(loginMB.getClient());
                    incomeTable.add(new IncomeData(et.getName(), b.getName(), income.getMonthlyNetto().doubleValue()));   
                    incomeSet.add(income);
                }
                else
                {
                    business.setBranch(b);
                    business.setEmploymentType(et);
                    business.setClient(loginMB.getClient());
                    incomeTable.add(new IncomeData(et.getName(), b.getName(), business.getIncomeCurrentYearNetto().doubleValue()));         
                    businessSet.add(business);
                }
    }
    
    public String saveAndRedirect()
    {
        System.out.println("Dalej");
        ClientDAO clientDao = new ClientDAO();
        
        Client client = loginMB.getClient();
        
        for (Region i : dictionaryMB.getRegion())
        {
            if (i.getIdRegion() == regionId)
            {
                address.setRegion(i);
                break;
            }
        }
        
        address.setClient(client);
        addressSet.add(address);
        
        client.setAddresses(addressSet);
        
        client.setName(name);
        client.setFamilyName(familyName);
        client.setLastName(surname);
        client.setBirthDate(birthDate);
        client.setPesel(pesel);
          
        client.setIncomes(incomeSet);
        client.setIncomeBuisnessActivities(businessSet);
        
        clientDao.update(client);
        
        return "/client/clientFinancialNeeds?faces-redirect=true";
    }

 
    
    public void toIncome()
    {
        setIsIncome(true);
        System.out.println(isIncome);
    }
    
    public void toBusinessActivity()
    {
        setIsIncome(false);
        System.out.println(isIncome);
    }


    public int getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(int loginNumber) {
        this.loginNumber = loginNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public int getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(int birthPlace) {
        this.birthPlace = birthPlace;
    }

    public ArrayList<IncomeData> getIncomeTable() {
        return incomeTable;
    }

    public void setIncomeTable(ArrayList<IncomeData> incomeTable) {
        this.incomeTable = incomeTable;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public IncomeBuisnessActivity getBusiness() {
        return business;
    }

    public void setBusiness(IncomeBuisnessActivity business) {
        this.business = business;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
           
    
}
