package com.efsf.sf.bean.client;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.joda.time.DateTime;

/**
 * @author WR1EI1
 */

@ManagedBean
@RequestScoped
public class ClientSettingsMB implements Serializable {
    private static final long serialVersionUID = 1L;
    //Update Settings
    
    @ManagedProperty(value="#{loginMB.client.idClient}")
    private Integer idClient;
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value="#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
   
    private Client client; 
    
    private String confirmPassword;
    
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
    
    private HashSet<Income> incomeSet = new HashSet();
    private HashSet<IncomeBusinessActivity> businessSet = new HashSet();
    
    private boolean tableEmpty = true;
    
    public ClientSettingsMB() { 
        clientDAO=new ClientDAO();
    }
 
    @PostConstruct
    private void loadClient() {
        client=clientDAO.readClientForSettings(idClient);
        
        idMartialStatus=client.getMaritalStatus().getIdMaritalStatus();
        idEducation=client.getEducation().getIdEducation();
        
        Iterator<Address> it=client.getAddresses().iterator();
        while(it.hasNext()){
        mainAddress=it.next();
        idMainRegion=mainAddress.getRegion().getIdRegion();
        }
        
        Iterator<Income> it2=client.getIncomes().iterator();
        while(it2.hasNext()){
        Income i=it2.next();
        IncomeData incomeData=new IncomeData(i.getEmploymentType().getName() , i.getBranch().getName() , i.getMonthlyNetto().longValue() );
        incomeData.setIdIncome(i.getIdIncome());
        incomeTable.add(incomeData);
        }
        
        Iterator<IncomeBusinessActivity> it3=client.getIncomeBusinessActivities().iterator();
        while(it3.hasNext()){
        IncomeBusinessActivity i=it3.next();
        IncomeData incomeData=new IncomeData(i.getEmploymentType().getName() , i.getBranch().getName() , i.getIncomeLastYearNetto().longValue() );
        incomeData.setIdIncomeBuisnessActivity(i.getIdIncomeBusinessActivity());
        incomeTable.add(incomeData);
        }
        
    }
    
    public String updateSettings() {
        
        return "/client/clientMainPage?faces-redirect=true";
    }
    
    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) 
    {       
        String password = (String) value;
        if (!password.equals(confirmPassword)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }      
    }

    
    
    
    
    
    
    
    //DIALOG 1:
    
    public void toIncome()
    {
        setIsIncome(true);
       
    }
    
    public void toBusinessActivity()
    {
        setIsIncome(false);
        
    }
    
    public void cleanDialog()
    {
       
        income = new Income();
        branchId = 0;
        incomeId = 0;
    }
    
    public void addIncome()
    {
                EmploymentType et = null; 
                for (EmploymentType i : dictionaryMB.getIncome() )
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
                    tableEmpty = false;
                    incomeTable.add(new IncomeData(et.getName(), b.getName(), income.getMonthlyNetto().doubleValue()));   
                    incomeSet.add(income);
                    income = new Income();
                }
                else
                {
                    
                    for (EmploymentType i : dictionaryMB.getBusinessActivity())
                    {
                          if (i.getIdEmploymentType() == incomeId)
                          {
                                 et = i;
                                 break;
                          }
                    }
                    business.setBranch(b);
                    business.setEmploymentType(et);
                    business.setClient(loginMB.getClient());
                    tableEmpty = false;
                    incomeTable.add(new IncomeData(et.getName(), b.getName(), business.getIncomeCurrentYearNetto().doubleValue()));        
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public HashSet<Income> getIncomeSet() {
        return incomeSet;
    }

    public void setIncomeSet(HashSet<Income> incomeSet) {
        this.incomeSet = incomeSet;
    }

    public HashSet<IncomeBusinessActivity> getBusinessSet() {
        return businessSet;
    }

    public void setBusinessSet(HashSet<IncomeBusinessActivity> businessSet) {
        this.businessSet = businessSet;
    }

    public boolean isTableEmpty() {
        return tableEmpty;
    }

    public void setTableEmpty(boolean tableEmpty) {
        this.tableEmpty = tableEmpty;
    }

    
    
}