package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MessagesMB;
import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.collection.ScheduleItem;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.dao.CaseStatusDAO;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.ProductDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.entity.Product;
import com.efsf.sf.sql.entity.ProductDetails;
import com.efsf.sf.util.Settings;
import com.efsf.sf.util.analyser.AnalyserAlgorithm;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.joda.time.LocalDate;
import org.primefaces.context.RequestContext;
import java.math.*;


@ManagedBean
@ViewScoped
public class CaseViewMB implements Serializable{
    
    

    @ManagedProperty(value="#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value = "#{messagesMB}")
    private MessagesMB messagesMB;
    
    
    
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    
    
     
    private ClientCase selectedClientCase;
    private Integer clientCaseId;
    private Consultant selectedConsultant;
    private Consultant selectedPremiumConsultant;
    private ProductDetails selectedProduct;
    
    private List<Product> productTree;
    
    private int caseStatusID;
    
    private List<IncomeData> selectedCaseIncomeTable = new ArrayList();
    
    private List<Consultant> premiumConsultants = new ArrayList();
    
    private List<ProductDetails> products = new ArrayList(); 
    
    private List<Integer> sortedProductValues;
   
    private List<ScheduleItem> schedule = new ArrayList();
    
    public CaseViewMB() {
    }
    
    
    
    public void loadCaseConsultantsDetails() throws IOException
    {   FacesContext facesContext = FacesContext.getCurrentInstance();
         if (!facesContext.isPostback() && !facesContext.isValidationFailed())
         {
            premiumConsultants = (ArrayList<Consultant>) bestConsultantsForPremiumCase();
            ClientCaseDAO cdao = new ClientCaseDAO();
            if (!cdao.checkClientAccess(loginMB.getClient().getIdClient(), clientCaseId)) 
            {
                facesContext.getExternalContext().redirect("./../error.xhtml");
            }
            selectedClientCase = cdao.getClientCaseWithConsultantDetails(clientCaseId);
            selectedProduct = selectedClientCase.getProductDetails();
                    
            generateSchedule();
         }
    }
    
    public void loadCaseClientsDetails() throws IOException           
    {   
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isValidationFailed())
        {

            ClientCaseDAO cdao = new ClientCaseDAO();
            if (!cdao.checkConsultantAccess(clientCaseId, loginMB.getConsultant().getIdConsultant()))
            {
                facesContext.getExternalContext().redirect("./../error.xhtml");
            }
            selectedClientCase = cdao.getClientCaseWithClientDetails(clientCaseId);
            caseStatusID = selectedClientCase.getCaseStatus().getIdCaseStatus();
            selectedClientCase.setViewCounter(selectedClientCase.getViewCounter()+ 1);      
            cdao.updateClientCase(selectedClientCase);
            fillSelectedCaseIncomeTable();
        }
    }
    
    
    public List<Consultant> bestConsultantsForPremiumCase(){
        List<Consultant> cons;
        
        AnalyserAlgorithm aa = new AnalyserAlgorithm(clientCaseId);
        cons = aa.getBestConsultants();
        
        return cons;
    }
    
    public void showActualAssignedProductConsultantView()
    {
        selectedProduct=selectedClientCase.getProductDetails();
        fetchProductTreeForSelectedProduct(selectedProduct);
    }
    
    public void findBestProduct()
    {
        AnalyserAlgorithm aa = new AnalyserAlgorithm(clientCaseId, loginMB.getConsultant().getIdConsultant());
        Map<Integer, Integer> productsIdsWithValue = aa.getBestOfferts();
        if (productsIdsWithValue != null && !productsIdsWithValue.isEmpty() )
        {
           sortedProductValues = new ArrayList(productsIdsWithValue.values());
           Collections.sort(sortedProductValues);
           Collections.reverse(sortedProductValues);
            products = aa.bestProductForCase(productsIdsWithValue);
        }
        else
        {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("emptyProducts.show()");
        }
    }
    
    public Product fetchTopProductForProductDetails(ProductDetails pd)
    {
        ProductDAO pdao = new ProductDAO();
        
        Product p = pdao.getProductForDetails(pd.getIdProductDetail());
        
        while (p.getParentProduct() != 0)
        {
            p = pdao.getProductWithInstitution(p.getParentProduct());
        }
        return p;
    }
    
    public void fetchProductTreeForSelectedProduct(ProductDetails pd)
    {
        ProductDAO pdao = new ProductDAO();
        
        Product p = pdao.getProductForDetails(pd.getIdProductDetail());
        
        productTree = new ArrayList();
        
        productTree.add(p);
        
        while (p.getParentProduct() != 0)
        {
            p = pdao.getProductWithInstitution(p.getParentProduct());
            productTree.add(p);
        }        
        
        Collections.reverse(productTree);
    }
    
    public void assignProduct(ProductDetails pd)
    {
        
        ClientCaseDAO cdao = new ClientCaseDAO();
        CaseStatus cs = new CaseStatusDAO().read(Settings.PRODUCT_ASSIGNED);
        selectedClientCase.setCaseStatus(cs);
        selectedClientCase.setProductDetails(pd);
        
        messagesMB.generateSystemMessage(bundle.getString("PRODUCT_ASSIGNED"), selectedClientCase.getClient().getUser().getIdUser(), new Object[] {selectedClientCase.getIdClientCase() } );
        
        cdao.updateClientCase(selectedClientCase);
    }

    public void haha()
    {
        System.out.println(selectedClientCase.getInterestRateType());
    }
    
    public void changeCaseStatus()
    {
        
        RequestContext context = RequestContext.getCurrentInstance();
        
        if (caseStatusID == 9)
        {
            selectedClientCase.setFreeResourcesValue(BigDecimal.ZERO);
            context.execute("fillFinishedData.show();");
        }
        else
        {
            changeCaseStatus(caseStatusID);
            context.execute("statusChange.show();");
        }
    }
    
    public void validateFinishedDates(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
    
            
            LocalDate ld = new LocalDate((Date) value);
            Boolean notValid = false;
            
            if (component.getId().equals("beginDate"))
            {
                 org.primefaces.component.calendar.Calendar componentValue = (org.primefaces.component.calendar.Calendar) component.getAttributes().get("receiveDateValue");
                 Date date = (Date) componentValue.getValue();
                 if (ld.isBefore(new LocalDate(date)))
                 {
                     notValid = true;
                 }
           }           
           if(notValid)
           {
               
               
                FacesMessage msg = new FacesMessage(bundle.getString("wrongDate"), bundle.getString("wrongDate"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
    }
    
    public void changeCaseStatus(int id)
    {
           CaseStatus cs = new CaseStatusDAO().read(id);
            String before = selectedClientCase.getCaseStatus().getName();
            selectedClientCase.setCaseStatus(cs);
            new ClientCaseDAO().updateClientCase(selectedClientCase);
            String after = selectedClientCase.getCaseStatus().getName();           
            messagesMB.generateSystemMessage(bundle.getString("STATUS_CHANGED"), selectedClientCase.getClient().getUser().getIdUser(), new Object[] {clientCaseId, before, after});
            
    }
    
    public void fillSelectedCaseIncomeTable() {
       
        selectedCaseIncomeTable = new ArrayList();
        
        Client client = new ClientDAO().getClientWithIncomes(getSelectedClientCase().getClient().getIdClient());
        
        for (Income i : client.getIncomes())
        {
            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getMonthlyNetto().doubleValue()));
        }
        
        for (IncomeBusinessActivity i : client.getIncomeBusinessActivities())
        {
            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getIncomeLastYearNetto().doubleValue()));
        }
    }
    
    public boolean checkLoggedConsultantAccessToCase()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        return caseDao.checkConsultantAccess(clientCaseId, loginMB.getConsultant().getIdConsultant());
                
    }
    
    
    public List<Consultant> castConsultantSetToArray(Set<Consultant> cSet)
    {
        return new ArrayList(cSet);
    }
    
    public void assignPremiumConsultant()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        selectedClientCase.setConsultant(selectedPremiumConsultant);
        
        caseDao.updateClientCase(selectedClientCase);  
    }
    
    public void assignConsultant()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        CaseStatusDAO statusDao = new CaseStatusDAO();
        
        
        selectedClientCase.setConsultant(selectedConsultant);
        selectedClientCase.setCaseStatus(statusDao.read(2));
        selectedClientCase.setConsultants(null);
        selectedClientCase.setConsultants_1(null);
        caseDao.updateClientCase(selectedClientCase);  
        
        messagesMB.generateSystemMessage(bundle.getString("CLIENT_SELECTED_CONSULTANT"), selectedConsultant.getUser().getIdUser(), new Object[] {loginMB.getClient().getIdClient(), selectedClientCase.getIdClientCase()}); 
    }
    
    public void rowDoubleClick(Consultant cos) throws IOException
    {   
        FacesContext.getCurrentInstance().getExternalContext().redirect("clientConsultantProfileView.xhtml?idConsultant=" + cos.getIdConsultant()); 
    }
    
    public boolean isNotRated(){
        CaseRatingDAO dao = new CaseRatingDAO();
        return dao.isNotRated(getSelectedClientCase().getIdClientCase());
    }
    
    public String toRateCase()
    {
        clientCaseMB.setCurrentlyRatedCase(selectedClientCase);
        return "/client/clientConsultantRate.xhtml?faces-redirect=true";
    }

    public ClientCase getSelectedClientCase() {
        return selectedClientCase;
    }

    public void setSelectedClientCase(ClientCase selectedClientCase) {
        this.selectedClientCase = selectedClientCase;
    }

    public Integer getClientCaseId() {
        return clientCaseId;
    }

    public void setClientCaseId(Integer clientCaseId) {
        this.clientCaseId = clientCaseId;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public MessagesMB getMessagesMB() {
        return messagesMB;
    }

    public void setMessagesMB(MessagesMB messagesMB) {
        this.messagesMB = messagesMB;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Consultant getSelectedConsultant() {
        return selectedConsultant;
    }

    public void setSelectedConsultant(Consultant selectedConsultant) {
        this.selectedConsultant = selectedConsultant;
    }

    public Consultant getSelectedPremiumConsultant() {
        return selectedPremiumConsultant;
    }

    public void setSelectedPremiumConsultant(Consultant selectedPremiumConsultant) {
        this.selectedPremiumConsultant = selectedPremiumConsultant;
    }

    public int getCaseStatusID() {
        return caseStatusID;
    }

    public void setCaseStatusID(int caseStatusID) {
        this.caseStatusID = caseStatusID;
    }

    public List<IncomeData> getSelectedCaseIncomeTable() {
        return selectedCaseIncomeTable;
    }

    public void setSelectedCaseIncomeTable(List<IncomeData> selectedCaseIncomeTable) {
        this.selectedCaseIncomeTable = selectedCaseIncomeTable;
    }

    public List<Consultant> getPremiumConsultants() {
        return premiumConsultants;
    }

    public void setPremiumConsultants(List<Consultant> premiumConsultants) {
        this.premiumConsultants = premiumConsultants;
    }

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }
    
    
    public void generateSchedule() {

        schedule = new ArrayList();

        if (selectedClientCase.getConsolidationValue() != null && selectedClientCase.getExpectedInstalment() != null
                && selectedClientCase.getInterestRate() != null) {
            
            Double toPay = selectedClientCase.getConsolidationValue().doubleValue();
            Double instalment = selectedClientCase.getExpectedInstalment().doubleValue();
            Double intrestRate = selectedClientCase.getInterestRate().doubleValue();
            calculatePayements(toPay, intrestRate, instalment);
        }

    }   

    public void setSchedule(List<ScheduleItem> schedule) {
        this.schedule = schedule;
    }

    public List<ProductDetails> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetails> products) {
        this.products = products;
    }

    public List<Integer> getSortedProductValues() {
        return sortedProductValues;
    }

    public void setSortedProductValues(List<Integer> sortedProductValues) {
        this.sortedProductValues = sortedProductValues;
    }

    public ProductDetails getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductDetails selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Product> getProductTree() {
        return productTree;
    }

    public void setProductTree(List<Product> productTree) {
        this.productTree = productTree;
    }

    public List<ScheduleItem> getSchedule() {
        return schedule;
    }

    private void calculatePayements(Double toPay, Double intrestRate, Double instalment) {
        Double alreadyPayed=0.0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedClientCase.getBeginPaymentDate());
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(selectedClientCase.getReceiveCreditDate());
        
        long diff = cal.getTimeInMillis() - cal2.getTimeInMillis();
        double breakPediodInDays=diff / (24 * 60 * 60 * 1000);

        
        toPay*=(1+((0.01*intrestRate*breakPediodInDays)/365));
        
        //RATA STAŁA
        if(selectedClientCase.getInterestRateType()==0){
            
            while(toPay>instalment){
                toPay-=instalment;
                alreadyPayed+=instalment;
                getSchedule().add(new ScheduleItem(cal.getTime(), instalment, alreadyPayed, toPay));
                cal.add(Calendar.MONTH, 1);
                toPay*=(1+((0.01*intrestRate)/12));
            }
            
            //Ostatnia rata nierówna
            getSchedule().add(new ScheduleItem(cal.getTime(), toPay, alreadyPayed+toPay, 0.0));
            
            
        } else {
            double instalment2;
            
             while(toPay>instalment){
                
                instalment2=toPay*((0.01*intrestRate)/12);
                toPay-=instalment;
                toPay-=instalment2;
                
                alreadyPayed+=instalment;
                alreadyPayed+=instalment2;
                
                getSchedule().add(new ScheduleItem(cal.getTime(), instalment+instalment2, alreadyPayed, toPay));
                cal.add(Calendar.MONTH, 1);
                toPay*=(1+((0.01*intrestRate)/12));
            }
            
            //Ostatnia rata nierówna
            getSchedule().add(new ScheduleItem(cal.getTime(), toPay, alreadyPayed+toPay, 0.0));           
            
        }
    }
}
