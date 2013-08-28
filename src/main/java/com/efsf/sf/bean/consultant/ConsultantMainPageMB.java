package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MessagesMB;
import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.SubscriptionDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.util.Algorithms;
import com.efsf.sf.util.Converters;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;
import org.joda.time.Days;

@ManagedBean
@SessionScoped
public class ConsultantMainPageMB {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    @ManagedProperty(value = "#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    @ManagedProperty(value = "#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    @ManagedProperty(value = "#{messagesMB}")
    private MessagesMB messagesMB;
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    private List<ClientCase> clientCaseList = new ArrayList();
    private List<ClientCase> appliedList = new ArrayList();
    private List<ClientCase> observedList = new ArrayList();
    private List<ClientCase> ownedList = new ArrayList();
    private List<ClientCase> finishedList = new ArrayList();
    private List<ClientCase> premiumList = new ArrayList();
    private Converters converters = new Converters();
    private ClientCaseDAO caseDao = new ClientCaseDAO();
    private ClientCase selectedLastCase;
    private ClientCase selectedObservedCase;
    private ClientCase selectedAppliedCase;
    private ClientCase selectedOwnedCase;
    private ClientCase selectedFinishedCase;
    private List<Set<String>> modelsEmploymentType = new ArrayList();
    private List<Set<String>> modelsBranch = new ArrayList();
    private List<Set<String>> observedModelsEmploymentType = new ArrayList();
    private List<Set<String>> observedModelsBranch = new ArrayList();
    private List<Set<String>> appliedModelsEmploymentType = new ArrayList();
    private List<Set<String>> appliedModelsBranch = new ArrayList();
    private List<Set<String>> ownedModelsEmploymentType = new ArrayList();
    private List<Set<String>> ownedModelsBranch = new ArrayList();
    private List<Set<String>> finishedModelsEmploymentType = new ArrayList();
    private List<Set<String>> finishedModelsBranch = new ArrayList();
    private ClientCase selectedPremiumCase;

    public ConsultantMainPageMB() {
    }

    public void reload() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isValidationFailed() && !facesContext.isPostback()) {
            pollData();
        }
    }

    public String toMarket() {
        return "/consultant/consultantMarket?faces-redirect=true";
    }

    public boolean isProfilNotFilled(){
        return Algorithms.calculateProgress(loginMB.getConsultant())<30;
    }
    
    // May be moved to ClientCaseMB
    public void selectPremiumCase(ClientCase cc) {
        selectedPremiumCase = cc;
    }

    //May be moved to ClientCaseMB
    public void loadPremiumAppliences() {
        premiumList = caseDao.premiumCasesSelectedConsultant(loginMB.getConsultant().getIdConsultant());
    }

    public void reloadOwnedTable() {
        ownedList = caseDao.ownedCasesSelectedConsultant(loginMB.getConsultant().getIdConsultant());
        ownedModelsEmploymentType = new ArrayList();
        ownedModelsBranch = new ArrayList();
        for (int i = 0; i < ownedList.size(); i++) {
            ownedModelsEmploymentType.add(showAllClientsEmploymentTypes(ownedList.get(i).getClient()));
            ownedModelsBranch.add(showAllClientsBranches(ownedList.get(i).getClient()));
        }

    }

    public void reloadFinishedTable() {
        finishedList = caseDao.finishedCasesSelectedConsultant(loginMB.getConsultant().getIdConsultant());
        finishedModelsEmploymentType = new ArrayList();
        finishedModelsBranch = new ArrayList();
        for (int i = 0; i < finishedList.size(); i++) {
            finishedModelsEmploymentType.add(showAllClientsEmploymentTypes(finishedList.get(i).getClient()));
            finishedModelsBranch.add(showAllClientsBranches(finishedList.get(i).getClient()));
        }

    }

    public void reloadAppliedTable() {
        appliedList = castClientCaseSetToArray(loginMB.getConsultant().getClientCases());

        appliedModelsEmploymentType = new ArrayList();
        appliedModelsBranch = new ArrayList();

        for (int i = 0; i < appliedList.size(); i++) {
            appliedModelsEmploymentType.add(showAllClientsEmploymentTypes(appliedList.get(i).getClient()));
            appliedModelsBranch.add(showAllClientsBranches(appliedList.get(i).getClient()));
        }
    }

    public void reloadObservedTable() {
        observedList = castClientCaseSetToArray(loginMB.getConsultant().getClientCases_2());
        observedModelsEmploymentType = new ArrayList();
        observedModelsBranch = new ArrayList();

        for (int i = 0; i < observedList.size(); i++) {
            observedModelsEmploymentType.add(showAllClientsEmploymentTypes(observedList.get(i).getClient()));
            observedModelsBranch.add(showAllClientsBranches(observedList.get(i).getClient()));
        }
    }

    public void rowDoubleClick(ClientCase cs) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("consultantCaseDetails.xhtml?clientCaseId=" + cs.getIdClientCase());
    }

    public List<ClientCase> castClientCaseSetToArray(Set<ClientCase> csSet) {
        ArrayList<ClientCase> ccArray = new ArrayList(csSet);

        Collections.sort(ccArray, new Comparator<ClientCase>() {
            public int compare(ClientCase a, ClientCase b) {
                return Integer.signum(a.getIdClientCase() - b.getIdClientCase());
            }
        });

        return ccArray;
    }

    public void reloadCases() {
        modelsEmploymentType = new ArrayList();
        modelsBranch = new ArrayList();
        clientCaseList = caseDao.last5Cases();
        for (int i = 0; i < clientCaseList.size(); i++) {
            modelsEmploymentType.add(showAllClientsEmploymentTypes(clientCaseList.get(i).getClient()));
            modelsBranch.add(showAllClientsBranches(clientCaseList.get(i).getClient()));
        }
    }

    public String toClientCaseDetails(ClientCase cc) {
        return "/consultant/consultantCaseDetails?faces-redirect=true&clientCaseId=" + cc.getIdClientCase();
    }

    // This method may be moved to ClientCaseMB
    public void acceptPremiumApplication(ClientCase cc) {
        clientCaseMB.consultantAcceptPremium(cc);
        premiumList.remove(cc);
    }

    //This method may be moved to ClientCaseMB
    public void revokePremiumApplication(ClientCase cc) {
        premiumList.remove(cc);
        clientCaseMB.consultantRevokePremium(cc);
    }

    public void pollData() {
        selectedLastCase = null;
        selectedObservedCase = null;
        selectedAppliedCase = null;
        selectedFinishedCase = null;
        selectedOwnedCase = null;

        // IT COULD BE BETTER TO JUST UPDATE CASES CONNECTED TO THE CONSULTANT NOT THE WHOLE CONSULTANT //TODO
        loginMB.setConsultant(new ConsultantDAO().getCounsultantConnectedToUser(loginMB.getIdUser()));
        loadPremiumAppliences();
        messagesMB.loadUnreadMessages();
        reloadCases();
        reloadAppliedTable();
        reloadOwnedTable();
        reloadFinishedTable();
        reloadObservedTable();

    }

    public void observeCase(ClientCase cs) {
        clientCaseMB.traceCase(cs);
        if (!clientCaseMB.isAlreadyObserved()) {
            selectedObservedCase = null;
            reloadObservedTable();
        }
    }

    public void applyToCase(ClientCase cs) {
        clientCaseMB.applyToCase(cs);
        if (!clientCaseMB.isAlreadyApplied()) {
            selectedAppliedCase = null;
            reloadAppliedTable();
        }
    }

    public void stopObserveCase(ClientCase cc) {
        clientCaseMB.stopObserve(cc);
        selectedObservedCase = null;
        reloadObservedTable();
    }

    public void revokeAppliedCase(ClientCase cc) {
        clientCaseMB.revokeApplication(cc);
        selectedAppliedCase = null;
        reloadAppliedTable();

    }

    public int countConsultantApplications(ClientCase cs) {
        Set<Consultant> cons = cs.getConsultants();
        return (cons == null) ? 0 : cons.size();
    }

    public Set<String> showAllClientsEmploymentTypes(Client client) {
        HashSet<String> types = new HashSet();
        if (client.getIncomes() != null) {
            for (Income i : client.getIncomes()) {
                types.add(i.getEmploymentType().getShortcut());
            }
        }
        if (client.getIncomeBusinessActivities() != null) {
            for (IncomeBusinessActivity i : client.getIncomeBusinessActivities()) {
                types.add(i.getEmploymentType().getShortcut());
            }
        }

        return types;
    }

    public Set<String> showAllClientsBranches(Client client) {
        HashSet<String> types = new HashSet();
        if (client.getIncomes() != null) {
            for (Income i : client.getIncomes()) {
                types.add(i.getBranch().getName());
            }
        }
        if (client.getIncomeBusinessActivities() != null) {
            for (IncomeBusinessActivity i : client.getIncomeBusinessActivities()) {
                types.add(i.getBranch().getName());
            }
        }

        return types;
    }

    public boolean showBIK(Client client) {
        if (client.getRequiredDocumentses().isEmpty()) {
            return false;
        } else {
            RequiredDocuments rds = client.getRequiredDocumentses().iterator().next();
            return rds.getBik() == null;
        }
    }

    public int subscriptionDeadline() {
        int days = 0;

        SubscriptionDAO sdao = new SubscriptionDAO();
        Subscription s = sdao.loadFkConsultant(loginMB.getConsultant().getIdConsultant());

        Date d1 = s.getDateTo();
        DateTime dataTime = new DateTime();
        DateTime dataTime2 = new DateTime(d1);

        days = Days.daysBetween(dataTime, dataTime2).getDays();
        if (days < 0) {
            days = 0;
        }

        return days;
    }

    public ClientCaseDAO getCaseDao() {
        return caseDao;
    }

    public void setCaseDao(ClientCaseDAO caseDao) {
        this.caseDao = caseDao;
    }

    public List<Set<String>> getModelsEmploymentType() {
        return modelsEmploymentType;
    }

    public void setModelsEmploymentType(List<Set<String>> modelsEmploymentType) {
        this.modelsEmploymentType = modelsEmploymentType;
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

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
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

    public List<ClientCase> getClientCaseList() {
        return clientCaseList;
    }

    public void setClientCaseList(List<ClientCase> clientCaseList) {
        this.clientCaseList = clientCaseList;
    }

    public List<ClientCase> getOwnedList() {
        return ownedList;
    }

    public void setOwnedList(List<ClientCase> ownedList) {
        this.ownedList = ownedList;
    }

    public List<ClientCase> getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(List<ClientCase> finishedList) {
        this.finishedList = finishedList;
    }

    public List<ClientCase> getPremiumList() {
        return premiumList;
    }

    public void setPremiumList(List<ClientCase> premiumList) {
        this.premiumList = premiumList;
    }

    public Converters getConverters() {
        return converters;
    }

    public void setConverters(Converters converters) {
        this.converters = converters;
    }

    public ClientCase getSelectedLastCase() {
        return selectedLastCase;
    }

    public void setSelectedLastCase(ClientCase selectedLastCase) {
        this.selectedLastCase = selectedLastCase;
    }

    public ClientCase getSelectedObservedCase() {
        return selectedObservedCase;
    }

    public void setSelectedObservedCase(ClientCase selectedObservedCase) {
        this.selectedObservedCase = selectedObservedCase;
    }

    public ClientCase getSelectedAppliedCase() {
        return selectedAppliedCase;
    }

    public void setSelectedAppliedCase(ClientCase selectedAppliedCase) {
        this.selectedAppliedCase = selectedAppliedCase;
    }

    public ClientCase getSelectedOwnedCase() {
        return selectedOwnedCase;
    }

    public void setSelectedOwnedCase(ClientCase selectedOwnedCase) {
        this.selectedOwnedCase = selectedOwnedCase;
    }

    public ClientCase getSelectedFinishedCase() {
        return selectedFinishedCase;
    }

    public void setSelectedFinishedCase(ClientCase selectedFinishedCase) {
        this.selectedFinishedCase = selectedFinishedCase;
    }

    public List<Set<String>> getModelsBranch() {
        return modelsBranch;
    }

    public void setModelsBranch(List<Set<String>> modelsBranch) {
        this.modelsBranch = modelsBranch;
    }

    public List<Set<String>> getObservedModelsEmploymentType() {
        return observedModelsEmploymentType;
    }

    public void setObservedModelsEmploymentType(List<Set<String>> observedModelsEmploymentType) {
        this.observedModelsEmploymentType = observedModelsEmploymentType;
    }

    public List<Set<String>> getObservedModelsBranch() {
        return observedModelsBranch;
    }

    public void setObservedModelsBranch(List<Set<String>> observedModelsBranch) {
        this.observedModelsBranch = observedModelsBranch;
    }

    public List<Set<String>> getAppliedModelsEmploymentType() {
        return appliedModelsEmploymentType;
    }

    public void setAppliedModelsEmploymentType(List<Set<String>> appliedModelsEmploymentType) {
        this.appliedModelsEmploymentType = appliedModelsEmploymentType;
    }

    public List<Set<String>> getAppliedModelsBranch() {
        return appliedModelsBranch;
    }

    public void setAppliedModelsBranch(List<Set<String>> appliedModelsBranch) {
        this.appliedModelsBranch = appliedModelsBranch;
    }

    public List<Set<String>> getOwnedModelsEmploymentType() {
        return ownedModelsEmploymentType;
    }

    public void setOwnedModelsEmploymentType(List<Set<String>> ownedModelsEmploymentType) {
        this.ownedModelsEmploymentType = ownedModelsEmploymentType;
    }

    public List<Set<String>> getOwnedModelsBranch() {
        return ownedModelsBranch;
    }

    public void setOwnedModelsBranch(List<Set<String>> ownedModelsBranch) {
        this.ownedModelsBranch = ownedModelsBranch;
    }

    public List<Set<String>> getFinishedModelsEmploymentType() {
        return finishedModelsEmploymentType;
    }

    public void setFinishedModelsEmploymentType(List<Set<String>> finishedModelsEmploymentType) {
        this.finishedModelsEmploymentType = finishedModelsEmploymentType;
    }

    public List<Set<String>> getFinishedModelsBranch() {
        return finishedModelsBranch;
    }

    public void setFinishedModelsBranch(List<Set<String>> finishedModelsBranch) {
        this.finishedModelsBranch = finishedModelsBranch;
    }

    public ClientCase getSelectedPremiumCase() {
        return selectedPremiumCase;
    }

    public void setSelectedPremiumCase(ClientCase selectedPremiumCase) {
        this.selectedPremiumCase = selectedPremiumCase;
    }

    public List<ClientCase> getAppliedList() {
        return appliedList;
    }

    public void setAppliedList(List<ClientCase> appliedList) {
        this.appliedList = appliedList;
    }

    public List<ClientCase> getObservedList() {
        return observedList;
    }

    public void setObservedList(List<ClientCase> observedList) {
        this.observedList = observedList;
    }
}
