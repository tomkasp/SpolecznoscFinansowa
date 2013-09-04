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

        //kwota konsolidacji + wolne srodki

//        this.klienci[0] = jedenKlient.getName();
//        this.klienci[1] = jedenKlient.getLastName();
//        this.klienci[2] = et1;
//        this.klienci[3] = sprKli.getConsolidationValue().doubleValue();
//        this.klienci[4] = sprKli.getExpectedInstalment().doubleValue();
//        this.klienci[5] = new Converters().ageFromBirthDate(jedenKlient.getBirthDate());

        clidata = new ClientData(jedenKlient.getName(), jedenKlient.getLastName(), 
                et1, sprKli.getConsolidationValue().doubleValue(), 
                sprKli.getExpectedInstalment().doubleValue(), 
                new Converters().ageFromBirthDate(jedenKlient.getBirthDate()));

        //klienci = kdao.getKlienci();
        //Produkty = kdao.getProduktyBankow();
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

//            produkty[prod][0] = ite.getIdProductDetail();
//            produkty[prod][1] = ite.getEmploymentType().getIdEmploymentType();
//            produkty[prod][2] = ite.getAmountBruttoMin().doubleValue();
//            produkty[prod][3] = ite.getAmountBruttoMax().doubleValue();
//            produkty[prod][4] = ite.getLoanTimeMin();
//            produkty[prod][5] = ite.getLoanTimeMax();
//            produkty[prod][6] = ite.getClientAgeMin().intValue();
//            produkty[prod][7] = ite.getClientAgeMax().intValue();

            prodata.add(new ProductsData(ite.getIdProductDetail(), ite.getEmploymentType().getIdEmploymentType(), 
                    ite.getAmountBruttoMin().doubleValue(), ite.getAmountBruttoMax().doubleValue(), 
                    ite.getLoanTimeMin(), ite.getLoanTimeMax(), ite.getClientAgeMin().intValue(), ite.getClientAgeMax().intValue()));
        }
    }

    private String analizuj(Integer idSprawaKlienta) {
        setClient(idSprawaKlienta);
        setProducts();
//        Arrays.fill(off, 0);

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


//        for (Map.Entry entry : testtab.entrySet()) {
//            System.out.println("wartosc tablicy nowej " + entry.getKey() + ", " + entry.getValue());
//        }

//        System.out.println("produkty lenght:" + produkty[0].length);
//        for (int z = 0; z < produkty.length; z++) {
//
//            //1.    jesli rodzaj dochodow klienta zgadza sie z dochodami akceptowalnymi zawartymi w szczegolach:
//            System.out.println("testuje produkty z 1 :" + (produkty[z][1]));
//            
//            // przeglada wszystkie dostepne typy dochodu klienta i zwieksza licznik.
//            
//           if( ((HashSet<Integer>)this.getKlienci()[2]).contains((Integer)produkty[z][1]) ){
//                off[(Integer)produkty[z][0]]++;
//                //System.out.println("ooooooooooooooooooooooooooooooooooooooooooooL :");
//            }
////            for(Integer kl :(HashSet<Integer>) this.getKlienci()[2]){
////                System.out.println("ooooooooooooooooooooooooooooooooooooooooooooL :" + kl);
////                System.out.println("ooooooooooooooooooooooooooooooooooooooooooooasd :" + produkty[z][1]);
////                
////                if(produkty[z][1].equals(kl)){
////                    off[(Integer)produkty[z][0]]++;
////                    
////                    
////                    break;
////                }
////            }
////            if (produkty[z][2] == (klienci[2])) {
////                off[Integer.valueOf(produkty[z][0].toString())]++;
////                System.out.println("tablica off w tym miejscu to : "+ off[Integer.valueOf(produkty[z][0].toString())] + " a index : " + Integer.valueOf(produkty[z][0].toString()) );
////            }
//
//            //2.    jesli kwota potrzebna klientowi jest w zasiegu danej oferty
//            if (((double)produkty[z][2] < (double)klienci[3]) && ((double)produkty[z][3] >= (double)klienci[3])) {
//               
//                off[(Integer)produkty[z][0]]++;
//            }
////
////            //3...  sprawdz okres kredytowania porzadana rata
////            // kwota konsolidacji/maxCzasKredytowania = oplata za miesiac
////            //jesli jest mniejsza lub rowna porzadanej racie klienta to ok! 
//            if (( (double)klienci[3] / (int)produkty[z][5]) <= ((double)klienci[4])) {
//                 System.out.println("DUPAAAAAAAAAAAAAAAAAAAAAA ==================================");
//                off[(Integer)produkty[z][0]]++;
//            }
////
////            //etap 4= porownanie wiekow 
//            if (  (int)produkty[z][6] <= (int)klienci[5]  && (int)produkty[z][7] > (int)klienci[5] ) {
//                off[(Integer)produkty[z][0]]++;
//            }
//
//        }

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
                if (entry.getValue() == max && tmp < 5) {

                    getBestOfferts().put((int) entry.getKey(), (int) entry.getValue());

                    System.out.println("WYKONUJE zapytanie dla sprawy o id= " + entry.getKey());
                    setBestConsultants(cdao.getConsultantsForProductDetail((int) entry.getKey()));
                    tmp++;
                }
            }
            max--;
        }

        //petla wpisujaca do tablicy tab[] indeksy najlepszych ofert.
//        //for (int z = 0; z <= 5; z++) {
////            for (int i = 0; i < oferta.length; i++) {
////                if (oferta[i] == max && tmp < 5) {
//                    //System.out.println("wykonala sie funkcja dla danych: oferta[i]:" + oferta[i] + ", max=" + max + ", tmp: " + tmp);
//                    tab[tmp][0] = i;
//                    tab[tmp][1] = oferta[i];
//                    System.out.println("WYKONUJE zapytanie dla sprawy o id= " + tab[tmp][0]);
//                    setWynik2(cdao.getConsultantsForProductDetail(tab[tmp][0]));
////                    System.out.println("dlugosc: " + wynik2.size());
////                    
////                    Iterator ia = wynik2.iterator();
////                   
////                    while(ia.hasNext()){
////                         Consultant co = (Consultant)ia.next();
////                        System.out.println("cos" + co.getName() + " " + co.getLastName());
////                        
////                    }
//                    tmp++;
//                }
//            }
//            max--;
        //}

        //TODO: zapisac do zmiennej tablicowej dane takie jak:
        //tablica[x][0]:nazwaBanku
        //tablica[x][1]:nazwaProduktu
        //tablica[x][2]:ocena (sila)
        //
        //System.out.println("tablica produktow to: " + oferta.length);
//        System.out.println("wygenerowana tablica wynikow : " + Arrays.deepToString(tab));

        for (Map.Entry ent : getBestOfferts().entrySet()) {
            System.out.println("oto co mam: " + ent.getKey() + " " + ent.getValue());
        }


        //ClientDAO kdaoa = new ClientDAO();
        //this.setWynik(kdaoa.ofertyWynikowe(tab));

        //System.out.println("po:"+Arrays.deepToString(this.wynik));

        // System.out.println("po:" + Arrays.toString(getWynik2().toArray()));

        //System.out.println("Nazwa naj Banku:" + this.getNazwaBanku());
        //System.out.println("Nazwa naj Produ:" + this.getNazwaProduktu());

    }
    //================================== pola ==================================
//    private Object[] klienci = new Object[6];
//    private Object[][] produkty = new Object[15][8];
    //TODO: tablica jest przypisana na stale -> 1000 wpisow, blad!
    //      ustawic rozmiar tablicy, w ogole przeanalizowac czy tablica off musi isc od 0 do 1000 czy po prostu numer ID wpisac... 
    // chyba jest ok co do OFF ale trzeba przeanalizowac!
//    private Integer[] off = new Integer[1000];
//    private Integer[][] tab = new Integer[5][2];
//    private String[][] wynik = new String[5][4];
//    private String nazwaBanku;
//    private String nazwaProduktu;

    //================================== /pola =================================
    //========================== gettery i settery =============================
    
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
