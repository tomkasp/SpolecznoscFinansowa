package com.efsf.sf.bean.client;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MessagesMB;
import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Algorithms;
import com.efsf.sf.util.SMSApi;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ClientCaseMB implements Serializable {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB login;
    @ManagedProperty(value = "#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    @ManagedProperty(value = "#{messagesMB}")
    private MessagesMB messagesMB;
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    private int idTypProduktu = 1;
    private int idTypProduktuObligation = 1;
    private ClientCase clientCase = new ClientCase();
    private Date currentDate = new Date();
    private Obligation obligation = new Obligation();
    private ProductTypeDAO ptd = new ProductTypeDAO();
    private List<Obligation> obligationList = new ArrayList<>();
    private ObligationDAO obdao = new ObligationDAO();
    private boolean alreadyApplied = false;
    private boolean notEnoughApplications = false;
    private boolean alreadyObserved = false;
    private int premium = 6;
    // VIEW CASE DETAILS FIELDS     
    private Obligation selectedObligation;
    private ArrayList<CaseStatus> statusModel = new ArrayList();
    private ClientCase currentlyRatedCase;
    private Integer caseDuration;

    /**
     * Creates a new instance of ClientCaseMB
     */
    public ClientCaseMB() {
    }

    @PostConstruct
    public void setModels() {
        for (CaseStatus cs : dictionaryMB.getCaseStatus()) {
            if (cs.getIdCaseStatus() >= 2 && cs.getIdCaseStatus() != 8) {
                statusModel.add(cs);
            }
        }
    }

    public List<Obligation> retrieveObligationListForCurrentClient() {

        setObligationList(obdao.obligationListForClient(login.getClient().getIdClient()));

        return obligationList;
    }

    public void delObligation() {

        obdao.deleteObligation(selectedObligation);

    }

    //zwraca liste zobowiazan dla danego klienta w sesji
    public void addObligation() {

        obligation.setClient(login.getClient());
        obligation.setProductType(ptd.getProductType(idTypProduktuObligation));
        obdao.save(obligation);
        obligation = new Obligation();
    }

    public void addMessage() {

        if (clientCase.getPremium()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Koszt to " + premium + " punktów!"));
        }

    }

    public Boolean premiumPointsChecking() {
        return login.getClient().getPoints() < premium;
    }

    public String addCase() {
        if (login.getClient().getPoints() > 0) {
            ClientCaseDAO ccd = new ClientCaseDAO();

            ClientDAO cd = new ClientDAO();

           
            Calendar c = Calendar.getInstance();
            c.setTime(clientCase.getBeginDate());
            c.add(Calendar.DATE, caseDuration);
            Date endDate = c.getTime();
            clientCase.setEndDate(endDate);


            if (clientCase.getPremium() == null) {
                clientCase.setPremium(false);
            }

            //pamietac o zabraniu punktow z klienta!
            if (clientCase.getPremium()) {
                cd.decrementPoints(login.getClient(), premium);
                login.getClient().setPoints(login.getClient().getPoints() - premium);
            } else {
                cd.decrementPoints(login.getClient(), 1);
                login.getClient().setPoints(login.getClient().getPoints() - 1);
            }

            //ucinanie do dwoch miejsc po przecinku bez zaokrlaglania!
            clientCase.setConsolidationValue(clientCase.getConsolidationValue().setScale(2, RoundingMode.DOWN));
            clientCase.setFreeResourcesValue(clientCase.getFreeResourcesValue().setScale(2, RoundingMode.DOWN));
            clientCase.setExpectedInstalment(clientCase.getExpectedInstalment().setScale(2, RoundingMode.DOWN));

            clientCase.setProductType(ptd.getProductType(idTypProduktu));
            clientCase.setClient(login.getClient());
            clientCase.setViewCounter(0);

            login.getClient().setObligations(new HashSet(obligationList));
            clientCase.setDifficulty(Algorithms.calculateCaseDifficulty(login.getClient(), clientCase));
            clientCase.setPhase(Algorithms.calculateProgress(login.getClient().getIdClient()));
            ccd.saveClientCase(clientCase);
            if (login.getClient().getPoints() == 0) {
                login.setActiveAddingApp(false);
            }
            clientCase = new ClientCase();
            caseDuration=null;
        }
        return "/client/clientMainPage.xhtml?faces-redirect=true";
    }

    // VIEW CASE METHODS 
    public void consultantAcceptPremium(ClientCase cc) {
        cc.setConsultants(null);
        cc.setConsultants_1(null);
        cc.setCaseStatus(new CaseStatusDAO().read(2));

        messagesMB.generateSystemMessage(bundle.getString("CONSULTANT_ACCEPT_PREMIUM"), cc.getClient().getUser().getIdUser(), new Object[]{login.getConsultant().getIdConsultant(), cc.getIdClientCase()});

        new ClientCaseDAO().updateClientCase(cc);
    }

    public void consultantRevokePremium(ClientCase cc) {


        cc.setConsultant(null);
        ConsultantRating conRating = new ConsultantRatingDAO().getConsultantRatings(login.getConsultant().getIdConsultant());

        if (conRating == null) {
            conRating = new ConsultantRating(login.getConsultant(), -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, 0);
        } else {
            conRating.setAverage(conRating.getAverage() - 0.5);
            conRating.setCompetence(conRating.getCompetence() - 0.5);
            conRating.setContact(conRating.getContact() - 0.5);
            conRating.setCulture(conRating.getCulture() - 0.5);
            conRating.setDifficulty(conRating.getDifficulty() - 0.5);
            conRating.setPunctuality(conRating.getPunctuality() - 0.5);
            conRating.setReliability(conRating.getReliability() - 0.5);
            conRating.setRespect(conRating.getRespect() - 0.5);
            conRating.setTrust(conRating.getTrust() - 0.5);
        }

        GenericDao<ConsultantRating> consRatingDao = new GenericDao(ConsultantRating.class);

        consRatingDao.saveOrUpdate(conRating);

        messagesMB.generateSystemMessage(bundle.getString("CONSULTANT_REVOKE_PREMIUM"), cc.getClient().getUser().getIdUser(), new Object[]{login.getConsultant().getIdConsultant(), cc.getIdClientCase()});

        new ClientCaseDAO().updateClientCase(cc);
    }

    public boolean doesConsultantObserveCase(Consultant consultant, ClientCase clientCase) {
        return new ClientCaseDAO().doesConsultantObserveCase(consultant.getIdConsultant(), clientCase.getIdClientCase());
    }

    public boolean doesAppliedToCase(Consultant consultant, ClientCase clientCase) {
        return new ClientCaseDAO().doesConsultantAppliedToCase(consultant.getIdConsultant(), clientCase.getIdClientCase());
    }

    public void stopObserve(ClientCase clientCase) {
        ConsultantDAO consultantDao = new ConsultantDAO();
        for (ClientCase cc : login.getConsultant().getClientCases_2()) {
            if (cc.getIdClientCase().equals(clientCase.getIdClientCase())) {
                login.getConsultant().getClientCases_2().remove(cc);
                consultantDao.merge(login.getConsultant());
                break;
            }
        }
    }

    public void revokeApplication(ClientCase clientCase) {
        ConsultantDAO consultantDao = new ConsultantDAO();
        for (ClientCase cc : login.getConsultant().getClientCases()) {
            if (cc.getIdClientCase().equals(clientCase.getIdClientCase())) {
                login.getConsultant().getClientCases().remove(cc);
                consultantDao.merge(login.getConsultant());
                break;
            }
        }
    }

    public void traceCase(ClientCase cs) {
        Consultant consultant = login.getConsultant();
        ConsultantDAO consultantDao;
        if (!new ClientCaseDAO().doesConsultantObserveCase(consultant.getIdConsultant(), cs.getIdClientCase())) {
            alreadyObserved = false;
            consultant.getClientCases_2().add(cs);
            consultantDao = new ConsultantDAO();
            consultantDao.merge(consultant);

        } else {
            alreadyObserved = true;
        }
    }

    public void applyToCase(ClientCase cs) {
        Consultant consultant = login.getConsultant();

        if (!new ClientCaseDAO().doesConsultantAppliedToCase(consultant.getIdConsultant(), cs.getIdClientCase()) && consultant.getApplayedCaseCounter() != 0 && login.returnConsultantAccessRights() > 0) {
            alreadyApplied = false;
            notEnoughApplications = false;
            consultant.getClientCases().add(cs);
            ConsultantDAO consultantDao = new ConsultantDAO();
            
            
            if (login.returnConsultantAccessRights() > 0 && login.returnConsultantAccessRights() < 3 )
            {
                consultant.setApplayedCaseCounter(consultant.getApplayedCaseCounter()-1);
            }
            consultantDao.merge(consultant);
            login.setConsultant(consultantDao.getCounsultantConnectedToUser(login.getIdUser()));

            
            GenericDao<Address> addressDao=new GenericDao(Address.class);
            Address address=addressDao.getWhere("fk_client", cs.getClient().getIdClient().toString()).get(0);
            if(address!=null && address.getPhone()!=null && address.getPhone().length()>8){
                SMSApi.sendSms(address.getPhone(), "Do obsługi Twojej sprawy na portalu SpolecznoscFinansowa.pl zgłosił się nowy doradca.");
            }
            
            messagesMB.generateSystemMessage(bundle.getString("CONSULTANT_APPLIED"), cs.getClient().getUser().getIdUser(), new Object[]{login.getConsultant().getIdConsultant(), cs.getIdClientCase()});
        } 
        else if (consultant.getApplayedCaseCounter() == 0)
        {
            notEnoughApplications = true;
        }
        else 
        {
            alreadyApplied = true;
        }
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public ClientCase getClientCase() {
        return clientCase;
    }

    public void setClientCase(ClientCase clientCase) {
        this.clientCase = clientCase;
    }

    public LoginMB getLogin() {
        return login;
    }

    public void setLogin(LoginMB login) {
        this.login = login;
    }

    public int getIdTypProduktu() {
        return idTypProduktu;
    }

    public void setIdTypProduktu(int idTypProduktu) {
        this.idTypProduktu = idTypProduktu;
    }

    public Obligation getObligation() {
        return obligation;
    }

    public void setObligation(Obligation obligation) {
        this.obligation = obligation;
    }

    public int getIdTypProduktuObligation() {
        return idTypProduktuObligation;
    }

    public void setIdTypProduktuObligation(int idTypProduktuObligation) {
        this.idTypProduktuObligation = idTypProduktuObligation;
    }

    public List<Obligation> getObligationList() {
        return obligationList;
    }

    public void setObligationList(List<Obligation> obligationList) {
        this.obligationList = obligationList;
    }

    public Obligation getSelectedObligation() {
        return selectedObligation;
    }

    public void setSelectedObligation(Obligation selectedObligation) {
        this.selectedObligation = selectedObligation;
    }

    public ArrayList<CaseStatus> getStatusModel() {
        return statusModel;
    }

    public void setStatusModel(ArrayList<CaseStatus> statusModel) {
        this.statusModel = statusModel;
    }

    public DictionaryMB getDictionaryMB() {
        return dictionaryMB;
    }

    public void setDictionaryMB(DictionaryMB dictionaryMB) {
        this.dictionaryMB = dictionaryMB;
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

    public boolean isAlreadyApplied() {
        return alreadyApplied;
    }

    public void setAlreadyApplied(boolean alreadyApplied) {
        this.alreadyApplied = alreadyApplied;
    }

    public boolean isAlreadyObserved() {
        return alreadyObserved;
    }

    public void setAlreadyObserved(boolean alreadyObserved) {
        this.alreadyObserved = alreadyObserved;
    }

    public ClientCase getCurrentlyRatedCase() {
        return currentlyRatedCase;
    }

    public void setCurrentlyRatedCase(ClientCase currentlyRatedCase) {
        this.currentlyRatedCase = currentlyRatedCase;
    }

    public Integer getCaseDuration() {
        return caseDuration;
    }

    public void setCaseDuration(Integer caseDuration) {
        this.caseDuration = caseDuration;
    }

    public boolean isNotEnoughApplications() {
        return notEnoughApplications;
    }

    public void setNotEnoughApplications(boolean notEnoughApplications) {
        this.notEnoughApplications = notEnoughApplications;
    }

    
}
