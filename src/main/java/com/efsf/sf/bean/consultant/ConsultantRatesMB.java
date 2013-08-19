package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.CaseRating;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.ConsultantRating;
import com.efsf.sf.collection.ScoreBoardRow;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@RequestScoped
public class ConsultantRatesMB implements Serializable{
    
    @ManagedProperty(value="#{loginMB.consultant.idConsultant}")
    private Integer idConsultant;
    
    @ManagedProperty(value="#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    @ManagedProperty(value="#{consultantMainPageMB}")
    private ConsultantMainPageMB consultantMainPageMB;
    
    private ClientCase clientCase;
    private List<ScoreBoardRow> scoreBoard;
   
    
    public String showConsulantRatesPage(){
        return "/consultant/consultantReceivedComments";
    }    
    
     public List<ClientCase> getConsulantRates(){
        CaseRatingDAO dao = new CaseRatingDAO();
        return dao.getConsultantRatings(idConsultant);
    }
     
   public void onRowSelect() throws IOException { 
        consultantMainPageMB.rowDoubleClick(clientCase);
        
    }
   
    public List<ScoreBoardRow> getScoreBoard() {
       GenericDao<ConsultantRating> dao = new GenericDao(ConsultantRating.class); 
       ConsultantRating rating=dao.getById(idConsultant);
       if(rating==null) rating=new ConsultantRating();
       
       List<ConsultantRating> ratingAll=dao.getAll();
       
       CaseRatingDAO caseDAO=new CaseRatingDAO();
       List<ClientCase> cases=caseDAO.getConsultantRatings(idConsultant);
       
       int[][] minAndMax=calculateConsultantMinAndMaxScore(cases);
       double[] average = calculateAverageConsultantScore(ratingAll);
    
       List<ScoreBoardRow> sb=new ArrayList<ScoreBoardRow>();
       sb.add(new ScoreBoardRow("Kontakt", rating.getContact(), average[0], minAndMax[1][0], minAndMax[0][0]));
       sb.add(new ScoreBoardRow("Kultura", rating.getCulture(), average[1], minAndMax[1][1], minAndMax[0][1]));
       sb.add(new ScoreBoardRow("Kompetencje", rating.getCompetence(), average[2], minAndMax[1][2], minAndMax[0][02]));
       sb.add(new ScoreBoardRow("Czas", rating.getPunctuality(), average[3], minAndMax[1][3], minAndMax[0][3]));
       sb.add(new ScoreBoardRow("Niezawodność", rating.getReliability(), average[4], minAndMax[1][4], minAndMax[0][4]));
       sb.add(new ScoreBoardRow("Szacunek", rating.getRespect(), average[5], minAndMax[1][5], minAndMax[0][5]));
       sb.add(new ScoreBoardRow("Trudność", rating.getDifficulty(), average[6], minAndMax[1][6], minAndMax[0][6]));
       sb.add(new ScoreBoardRow("Zaufanie", rating.getTrust(), average[7], minAndMax[1][7], minAndMax[0][7]));       
       return sb;
    }

   
    public Integer getIdConsultant() {
        return idConsultant;
    }

    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
    }

    public ClientCase getClientCase() {
        return clientCase;
    }

    public void setClientCase(ClientCase clientCase) {
        this.clientCase = clientCase;
    }


    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }

    public void setScoreBoard(List<ScoreBoardRow> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    private double[] calculateAverageConsultantScore(List<ConsultantRating> ratingAll) {
        double[] average=new double[8];
        for(ConsultantRating rat: ratingAll){
           average[0]+=rat.getContact();
           average[1]+=rat.getCulture();
           average[2]+=rat.getCompetence();
           average[3]+=rat.getPunctuality();
           average[4]+=rat.getReliability();
           average[5]+=rat.getRespect();
           average[6]+=rat.getDifficulty();
           average[7]+=rat.getTrust();
        }
        for(int i=0; i<average.length && !ratingAll.isEmpty(); i++)
            average[i]/=ratingAll.size();
        return average;
    }

    private int[][] calculateConsultantMinAndMaxScore(List<ClientCase> cases) {
        int[] min=new int[8];
        int[] max=new int[8];
        
        if(!cases.isEmpty()){
            for(int i=0; i<min.length; i++){
                min[i]=999;
                max[i]=-999;
            }
        }
        
        for(ClientCase cs: cases){
           CaseRating caseRat=cs.getCaseRating();
           if(caseRat.getContact()>max[0]) max[0]=caseRat.getContact();
           if(caseRat.getCulture()>max[1]) max[1]=caseRat.getCulture();
           if(caseRat.getCompetence()>max[2]) max[2]=caseRat.getCompetence();
           if(caseRat.getPunctuality()>max[3]) max[3]=caseRat.getPunctuality();
           if(caseRat.getReliability()>max[4]) max[4]=caseRat.getReliability();
           if(caseRat.getRespect()>max[5]) max[5]=caseRat.getRespect();
           if(caseRat.getDifficulty()>max[6]) max[6]=caseRat.getDifficulty();
           if(caseRat.getTrust()>max[7]) max[7]=caseRat.getTrust();
           
           if(caseRat.getContact()<min[0]) min[0]=caseRat.getContact();
           if(caseRat.getCulture()<min[1]) min[1]=caseRat.getCulture();
           if(caseRat.getCompetence()<min[2]) min[2]=caseRat.getCompetence();
           if(caseRat.getPunctuality()<min[3]) min[3]=caseRat.getPunctuality();
           if(caseRat.getReliability()<min[4]) min[4]=caseRat.getReliability();
           if(caseRat.getRespect()<min[5]) min[5]=caseRat.getRespect();
           if(caseRat.getDifficulty()<min[6]) min[6]=caseRat.getDifficulty();
           if(caseRat.getTrust()<min[7]) min[7]=caseRat.getTrust();
        }
        
        int[][] result=new int[2][8];
        for (int i=0; i<min.length; i++){
            result[0][i]=min[i];
            result[1][i]=max[i];
        }
    
        return result;        
        
    }

    public ConsultantMainPageMB getConsultantMainPageMB() {
        return consultantMainPageMB;
    }

    public void setConsultantMainPageMB(ConsultantMainPageMB consultantMainPageMB) {
        this.consultantMainPageMB = consultantMainPageMB;
    }
    

}
