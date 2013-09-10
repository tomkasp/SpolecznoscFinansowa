package com.efsf.sf.util.analyser;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.ProductDAO;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Converters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MadejsoN
 * @Date: 13.12.2012 Bean analizujacy obecne oferty bankow oraz dane przeslanego
 * klienta porownuje je i wybiera najlepszą (jedną lub wiele) ofert.
 *
 *
 * Klasa sklada sie z 3 metod... Pierwsza (pobierzDane) pobiera dane o kliencie
 * i jego wymaganiach oraz o dostepnych produktach z bankow druga ustala punkty
 * dla kazdego z produktow wg zadanych kryteriow. Trzecia metoda wyciaga
 * najlepsze 5 ofert i zapisuje do tablicy.
 *
 */
public class AnalyserAlgorithm {

    private List<Consultant> bestConsultants = null;
    private ClientData clidata = new ClientData();
    private ArrayList<ProductsData> prodata = new ArrayList<>();
    private HashMap<Integer, Integer> testtab = new HashMap<>();
    private HashMap<Integer, Integer> bestOfferts = new HashMap<>();
    private Integer idConsultant;
    
    public AnalyserAlgorithm() {
    }

    public AnalyserAlgorithm(Integer IdSprawy) {
        analizuj(IdSprawy);
    }
    
    public AnalyserAlgorithm(Integer idSprawy,Integer idConsultant) {
        this.idConsultant = idConsultant;
        analizuj(idSprawy);
    }
    
    public List<ProductDetails> bestProductForCase(Map<Integer, Integer> productIds) 
    {
        GenericDao<ProductDetails> dao = new GenericDao(ProductDetails.class);
        List<ProductDetails> productDetailsList = new ArrayList();
        Integer currentMax = Collections.max(productIds.values());
        while (currentMax > 0)
        {  
             for (Map.Entry <Integer, Integer> entry : productIds.entrySet())
             {
                 if (entry.getValue().equals(currentMax))
                 {
                     productDetailsList.add(dao.getById(entry.getKey()));
                 }
             }
             currentMax--;   
        }
        
        return productDetailsList;
    }

    private void setClient(Integer idSprawaKlienta) {
        ClientDAO kdao = new ClientDAO();
        ClientCaseDAO ccdao = new ClientCaseDAO();

        Client jedenKlient = kdao.getClientForCase(idSprawaKlienta);
        ClientCase sprKli = ccdao.read(idSprawaKlienta);

        HashSet<Integer> et1 = new HashSet();

        for (Income incomes : jedenKlient.getIncomes()) {
            et1.add(incomes.getEmploymentType().getIdEmploymentType());
        }
        for (IncomeBusinessActivity iba : jedenKlient.getIncomeBusinessActivities()) {
            et1.add(iba.getEmploymentType().getIdEmploymentType());
        }

        clidata = new ClientData(jedenKlient.getName(), jedenKlient.getLastName(), 
                et1, sprKli.getConsolidationValue().doubleValue(), 
                sprKli.getExpectedInstalment().doubleValue(), 
                new Converters().ageFromBirthDate(jedenKlient.getBirthDate()));

    }
    
    private void setProducts() {
        ProductDAO pDao = new ProductDAO();
        List<ProductDetails> pd=null; 
        
        //jesli jest nadany idConsultant wygeneruj oferty zgodne z konsultantem
        //jesli nie, wyswietl dla klienta najlepszych konsultantow
        if(this.idConsultant==null){
            pd = pDao.getAllProducts();
            System.out.println("nie został zapisany idConsultant");
        }
        else{
            pd = pDao.getAllProductsForConsultant(this.idConsultant);
            System.out.println("został zapisany konsultant");
        }
        
        Iterator i = pd.iterator();

        while (i.hasNext()) {
            ProductDetails ite = (ProductDetails) i.next();
            System.out.println(" cosik:" + ite.getAmountBruttoMax());

            prodata.add(new ProductsData(ite.getIdProductDetail(), ite.getEmploymentType().getIdEmploymentType(), 
                    ite.getAmountBruttoMin().doubleValue(), ite.getAmountBruttoMax().doubleValue(), 
                    ite.getLoanTimeMin(), ite.getLoanTimeMax(), ite.getClientAgeMin().intValue(), ite.getClientAgeMax().intValue()));
        }
    }

    private String analizuj(Integer idSprawaKlienta) {
        setClient(idSprawaKlienta);
        setProducts();

        //etap 1
        //dla pierwszego kryterium, znajdz wszystkie idProduktu ktory spelnia kryterium dochodow 

        Iterator iii = prodata.iterator();
        while (iii.hasNext()) {
            ProductsData pd = (ProductsData) iii.next();

            // przeglada wszystkie dostepne typy dochodu klienta i zwieksza licznik.
            if (clidata.getEmploymentType().contains(pd.getEmploymentType())) {
                //off[pd.getProductId()]++;
                testtab.put(pd.getProductId(), testtab.get(pd.getProductId()) == null ? 1 : testtab.get(pd.getProductId()) + 1);
            }

            //2.    jesli kwota potrzebna klientowi jest w zasiegu danej oferty
            if ((pd.getAmountBruttoMin() < clidata.getTotalValue()) && (pd.getAmountBruttoMax() >= clidata.getTotalValue())) {
                //off[pd.getProductId()]++;
                testtab.put(pd.getProductId(), testtab.get(pd.getProductId()) == null ? 1 : testtab.get(pd.getProductId()) + 1);
            }

            //3...  sprawdz okres kredytowania porzadana rata
            // kwota konsolidacji/maxCzasKredytowania = oplata za miesiac
            //jesli jest mniejsza lub rowna porzadanej racie klienta to ok!
            if ((clidata.getConsolidationValue() / pd.getLoanTimeMax()) <= clidata.getExpectedInstallment()) {
                // off[pd.getProductId()]++;
                testtab.put(pd.getProductId(), testtab.get(pd.getProductId()) == null ? 1 : testtab.get(pd.getProductId()) + 1);
            }

            //etap 4= porownanie wiekow 
            if (pd.getClientAgeMin() <= clidata.getClientAge() && pd.getClientAgeMax() >= clidata.getClientAge()) {
                // off[pd.getProductId()]++;
                testtab.put(pd.getProductId(), testtab.get(pd.getProductId()) == null ? 1 : testtab.get(pd.getProductId()) + 1);
            }
        }

        best5products(testtab);
        return "szczegolySprawy";
    }

    //metoda generuje 5 najlepszych produktow dla danej sprawy posortowanych malejaco.
    private void best5products(HashMap<Integer, Integer> oferta) {
        ConsultantDAO cdao = new ConsultantDAO();

        int tmp = 0;
        int max = 0;
        
        //sprwadzam max ocene do porownania (normalnie 4)
        for (Map.Entry entry : oferta.entrySet()) {
            if (max < (int) entry.getValue()) {
                max = (int) entry.getValue();
            }
        }

        for (int z = 0; z <= 5; z++) {
            for (Map.Entry entry : oferta.entrySet()) {
                if (entry.getValue().equals(max) && tmp < 5) {

                    getBestOfferts().put((int) entry.getKey(), (int) entry.getValue());

                    System.out.println("WYKONUJE zapytanie dla sprawy o id= " + entry.getKey());
                    setBestConsultants(cdao.getConsultantsForProductDetail((int) entry.getKey()));
                    tmp++;
                }
            }
            max--;
        }

        for (Map.Entry ent : getBestOfferts().entrySet()) {
            System.out.println("oto co mam: " + ent.getKey() + " " + ent.getValue());
        }

    }
    
    public List<Consultant> getBestConsultants() {
        return bestConsultants;
    }

    public void setBestConsultants(List<Consultant> bestConsultants) {
        this.bestConsultants = bestConsultants;
    }

    public HashMap<Integer, Integer> getBestOfferts() {
        return bestOfferts;
    }

    public void setBestOfferts(HashMap<Integer, Integer> bestOfferts) {
        this.bestOfferts = bestOfferts;
    }
}
