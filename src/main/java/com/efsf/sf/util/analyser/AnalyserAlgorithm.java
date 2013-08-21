package com.efsf.sf.util.analyser;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.ProductDAO;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Converters;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author MadejsoN
 * @Date: 13.12.2012 Bean analizujacy obecne oferty bankow oraz dane przeslanego
 * klienta porownuje je i wybiera najlepszą (jedną lub wiele) ofert.
 *
 * 
 * Klasa sklada sie z 3 metod... Pierwsza (pobierzDane) pobiera dane o kliencie i jego wymaganiach oraz o dostepnych produktach z bankow
 * druga ustala punkty dla kazdego z produktow wg zadanych kryteriow.
 * Trzecia metoda wyciaga najlepsze 5 ofert i zapisuje do tablicy. 
 * 
 */
public class AnalyserAlgorithm {

    public AnalyserAlgorithm() {
    }

    public AnalyserAlgorithm(Integer IdSprawy) {
        setClient(IdSprawy);
    }

    private void setClient(Integer idSprawaKlienta) {
        ClientDAO kdao = new ClientDAO();
        ClientCaseDAO ccdao = new ClientCaseDAO();
        
        Client jedenKlient = kdao.getClientForCase(idSprawaKlienta);
        ClientCase sprKli = ccdao.read(idSprawaKlienta);
        
        getKlienci()[0] = jedenKlient.getName();
        getKlienci()[1] = jedenKlient.getLastName();
        HashSet <Integer> et1 = new HashSet();   
        
        for(Income incomes: jedenKlient.getIncomes()){
            et1.add(incomes.getEmploymentType().getIdEmploymentType());
        }
        
        for(IncomeBusinessActivity iba: jedenKlient.getIncomeBusinessActivities()){
            et1.add(iba.getEmploymentType().getIdEmploymentType());
        }
        getKlienci()[2] = et1;
        //kwota konsolidacji + wolne srodki
        getKlienci()[3] = sprKli.getConsolidationValue();
        getKlienci()[4] = sprKli.getExpectedInstalment();
        getKlienci()[5] = new Converters().ageFromBirthDate(jedenKlient.getBirthDate());
        
        
        //klienci = kdao.getKlienci();
        //Produkty = kdao.getProduktyBankow();
    }
    
    
    public void setProducts(){
        //setProduktyBankow(new String[l2.size()][10]);
        //System.out.println("Produkty bankowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:" + getProduktyBankow().length);
        //Iterator it2 = l2.iterator();
        
        ProductDAO pDao = new ProductDAO();
        List<ProductDetails> pd = pDao.getAllProducts();
        
        Iterator i = pd.iterator();
        
        int prod = 0;
        while(i.hasNext()){
            ProductDetails ite = (ProductDetails)i.next();
            System.out.println(" cosik:" + ite.getAmountBruttoMax());
        
        
            produkty[prod][0] = ite.getIdProductDetail();
            produkty[prod][1] = ite.getEmploymentType().getIdEmploymentType();
            produkty[prod][2] = ite.getAmountBruttoMin();
            produkty[prod][3] = ite.getAmountBruttoMax();
            produkty[prod][4] = ite.getLoanTimeMin();
            produkty[prod][5] = ite.getLoanTimeMax();
            produkty[prod][6] = ite.getClientAgeMin();
            produkty[prod][7] = ite.getClientAgeMax();
            
            
            System.out.println("typ dochodu:"+ produkty[prod][0]);
            System.out.print("typ dochodu:"+ produkty[prod][1]);
            System.out.print("typ dochodu:"+ produkty[prod][2]);
            System.out.print("typ dochodu:"+ produkty[prod][3]);
            System.out.print("typ dochodu:"+ produkty[prod][4]);
            System.out.print("typ dochodu:"+ produkty[prod][5]);
            System.out.print("typ dochodu:"+ produkty[prod][6]);
            System.out.print("typ dochodu:"+ produkty[prod][7]);
            
            prod++;
        }

//        BigDecimal d1 = sprod.getAmountBruttoMin();
//        BigDecimal d2 = sprod.getAmountBruttoMax();
//
//        this.getProduktyBankow()[i][0] = String.valueOf(sprod.getIdProductDetail());
//        this.getProduktyBankow()[i][1] = String.valueOf(sprod.getProductType().getName());
//        this.getProduktyBankow()[i][2] = d1.toString();
//        this.getProduktyBankow()[i][3] = d2.toString();
//        this.getProduktyBankow()[i][4] = String.valueOf(sprod.getLoanTimeMin());
//        this.getProduktyBankow()[i][5] = String.valueOf(sprod.getLoanTimeMax());
//        this.getProduktyBankow()[i][6] = String.valueOf(sprod.getClientAgeMin());
//        this.getProduktyBankow()[i][7] = String.valueOf(sprod.getClientAgeMax());
//
//        System.out.println(i + 1 + " Typ Dochodu:" + this.getProduktyBankow()[i][1]);
//        
//        
//        System.out.println(Arrays.deepToString(getProduktyBankow()));
        
    }
    
    
    
    public static void main(String args[]){
        AnalyserAlgorithm aa = new AnalyserAlgorithm();
        aa.setClient(100);
        
        for(Integer i:(HashSet<Integer>)aa.getKlienci()[2]){
            System.out.println("wartosc:"+ i);
        }
        
        aa.setProducts();
               
        
        System.out.println("data:"+ aa.getKlienci()[5]);
        
    }

//    public String analizuj(Integer IdSprawaKlienta) {
//        pobierzDane(IdSprawaKlienta);
//        Arrays.fill(off, 0);
//
//        //etap 1
//        //dla pierwszego kryterium, znajdz wszystkie idProduktu ktory spelnia kryterium dochodow 
//        
//        System.out.println("produkty lenght:" + Produkty[0].length);
//        for (int z = 0; z < Produkty.length; z++) {
//
//            //1.    jesli rodzaj dochodow klienta zgadza sie z dochodami akceptowalnymi zawartymi w szczegolach:
//            System.out.println("testuje produkty z 1 :" + Integer.valueOf(Produkty[z][1]));
//            
//            
//            if (Integer.valueOf(Produkty[z][1]) == Integer.valueOf(klienci[2])) {
//                off[Integer.valueOf(Produkty[z][0])]++;
//            }
//
//            //2.    jesli kwota potrzebna klientowi jest w zasiegu danej oferty
//            if ((Double.valueOf(Produkty[z][2]) < Double.valueOf(klienci[3])) && (Double.valueOf(Produkty[z][3]) >= Double.valueOf(klienci[3]))) {
//                off[Integer.valueOf(Produkty[z][0])]++;
//            }
//
//            //3...  sprawdz okres kredytowania porzadana rata
//            // kwota konsolidacji/maxCzasKredytowania = oplata za miesiac
//            //jesli jest mniejsza lub rowna porzadanej racie klienta to ok! 
//            if ((Double.valueOf(klienci[3])) / (Double.valueOf(Produkty[z][5])) <= (Double.valueOf(klienci[4]))) {
//                off[Integer.valueOf(Produkty[z][0])]++;
//            }
//
//            //etap 4= porownanie wiekow 
//            if ((Integer.valueOf(Produkty[z][6]) <= Integer.valueOf(klienci[5])) && (Integer.valueOf(Produkty[z][7]) > Integer.valueOf(klienci[5]))) {
//                off[Integer.valueOf(Produkty[z][0])]++;
//            }
//
//        }
//
//        zwyciezaj(off);
//        //Arrays.fill(oferta, "");
//
//        for (int i = 0; i < off.length; i++) {
//            off[i] = null;
//        }
//
//        //metoda analizujaca, jako argument dostaje id klienta
//        return "szczegolySprawy";
//    }

    public void zwyciezaj(Integer[] oferta) {

        int tmp = 0;
        int max = 0;

        for (int i = 0; i < oferta.length; i++) {
            System.out.println("tablica Off[" + i + "]->" + oferta[i]);
            if (max < oferta[i]) {
                max = oferta[i];
            }
        }
        
        System.out.println("Wygenerowany maksik to: " + max);
        
        //petla wpisujaca do tablicy tab[] indeksy najlepszych ofert.
        for (int z = 0; z <= 5; z++) {
            for (int i = 0; i < oferta.length; i++) {
                if (oferta[i] == max && tmp < 5) {
                    //System.out.println("wykonala sie funkcja dla danych: oferta[i]:" + oferta[i] + ", max=" + max + ", tmp: " + tmp);
                    tab[tmp][0] = i;
                    tab[tmp][1] = oferta[i];
                    tmp++;
                }
            }
            max--;
        }

        //TODO: zapisac do zmiennej tablicowej dane takie jak:
        //tablica[x][0]:nazwaBanku
        //tablica[x][1]:nazwaProduktu
        //tablica[x][2]:ocena (sila)
        //
        System.out.println("tablica produktow to: " + oferta.length);
        
        System.out.println("wygenerowana tablica wynikow najnowsza to: " + Arrays.deepToString(tab));
        
        
        ClientDAO kdaoa = new ClientDAO();
        //this.setWynik(kdaoa.ofertyWynikowe(tab));

        System.out.println("po:"+Arrays.deepToString(this.wynik));
        
        //System.out.println("Nazwa naj Banku:" + this.getNazwaBanku());
        //System.out.println("Nazwa naj Produ:" + this.getNazwaProduktu());

    }
    //================================== pola ==================================
    private Object[] klienci = new Object[6];
    private Object[][] produkty = new Object[20][20];

    //TODO: tablica jest przypisana na stale -> 1000 wpisow, blad!
    //      ustawic rozmiar tablicy, w ogole przeanalizowac czy tablica off musi isc od 0 do 1000 czy po prostu numer ID wpisac... 
    // chyba jest ok co do OFF ale trzeba przeanalizowac!
    
    
    
    private Integer[] off = new Integer[1000];
    private Integer[][] tab = new Integer[5][2];
    
    private String[][] wynik = new String[5][4];
    
    private String nazwaBanku;
    private String nazwaProduktu;


    //================================== /pola =================================
    //========================== gettery i settery =============================
    

    public String getNazwaBanku() {
        return nazwaBanku;
    }

    public void setNazwaBanku(String nazwaBanku) {
        this.nazwaBanku = nazwaBanku;
    }

    public String getNazwaProduktu() {
        return nazwaProduktu;
    }

    public void setNazwaProduktu(String nazwaProduktu) {
        this.nazwaProduktu = nazwaProduktu;
    }

//    public Integer[][] getTab() {
//        return tab;
//    }
//
//    public void setTab(Integer[][] tab) {
//        this.tab = tab;
//    }

    public String[][] getWynik() {
        return wynik;
    }

    public void setWynik(String[][] wynik) {
        this.wynik = wynik;
    }

    public Object[] getKlienci() {
        return klienci;
    }

    public void setKlienci(Object[] klienci) {
        this.klienci = klienci;
    }
}
