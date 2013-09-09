package com.efsf.sf.util.bik;

public class RegexBase {
    
public static final RuleBase PESEL=new RuleBase("PESEL;(?<val>\\d*)", "PESEL");
public static final RuleBase KLASYFIKACJA_BIK=new RuleBase("Kod Klasyfikacji Uzupełniającej;(?<val>[0-9A-Z]*)", "KLASYFIKACJA_BIK");
public static final RuleBase OCENA_PUNKTOWA=new RuleBase("Ocena Punktowa;(?<val>[0-9]*)", "OCENA_PUNKTOWA");

//RACH ZAMK
public static final RuleBase RACH_ZAMK_ZALEG_0_30=new RuleBase("RACHUNKI ZAMKNIĘTE(.*?)Liczba Rachunków O Statusie „Zaległość 0-30 Dni”;(?<val>[0-9]*)", "RACH_ZAMK_ZALEG_0_30");
public static final RuleBase RACH_ZAMK_UMORZONY=new RuleBase("RACHUNKI ZAMKNIĘTE(.*?)Liczba Rachunków O Statusie „Umorzony”;(?<val>[0-9]*)", "RACH_ZAMK_UMORZONY");
public static final RuleBase RACH_ZAMK_ODZYSKANY=new RuleBase("RACHUNKI ZAMKNIĘTE(.*?)Liczba Rachunków O Statusie „Odzyskany”;(?<val>[0-9]*)", "RACH_ZAMK_ODZYSKANY");

//RACH OTW
public static final RuleBase RACH_OTW_ZALEG_0_30=new RuleBase("RACHUNKI OTWARTE(.*?)Liczba Rachunków O Statusie „Zaległość 0-30 Dni”;(?<val>[0-9]*)", "RACH_OTW_ZALEG_0_30");
public static final RuleBase RACH_OTW_ZALEG_31_90=new RuleBase("RACHUNKI OTWARTE(.*?)Liczba Rachunków O Statusie „Zaległość 31-90 Dni”;(?<val>[0-9]*)", "RACH_OTW_ZALEG_31_90");
public static final RuleBase RACH_OTW_ZALEG_91_180=new RuleBase("RACHUNKI OTWARTE(.*?)Liczba Rachunków O Statusie „Zaległość 91-180 Dni”;(?<val>[0-9]*)", "RACH_OTW_ZALEG_91_180");
public static final RuleBase RACH_OTW_ZALEG_180_WIECEJ=new RuleBase("RACHUNKI OTWARTE(.*?)Liczba Rachunków O Statusie „Zaległość Powyżej 180 Dni”;(?<val>[0-9]*)", "RACH_OTW_ZALEG_180_WIECEJ");
public static final RuleBase RACH_OTW_WINDYKACJA=new RuleBase("RACHUNKI OTWARTE(.*?)Liczba Rachunków O Statusie „Windykacja”;(?<val>[0-9]*)", "RACH_OTW_WINDYKACJA");
public static final RuleBase RACH_OTW_EGZEKUCJA=new RuleBase("RACHUNKI OTWARTE(.*?)Liczba Rachunków O Statusie „Egzekucja”;(?<val>[0-9]*)", "RACH_OTW_EGZEKUCJA");

public static final RuleBase RACHUNKI_KWESTIONOWANE=new RuleBase("Znaleziono Rachunki Kwestionowane;(?<val>[a-zA-Z]*)", "RACHUNKI_KWESTIONOWANE");
public static final RuleBase REKORDY_BIORK=new RuleBase("Znaleziono Zablokowane Rekordy BIORK;(?<val>[a-zA-Z]*)", "REKORDY_BIORK");


///////////////////////////////////////
//DANE DO ZAPYTAŃ
//////////////////////////////////////
public static final RuleBase Z_POWOD=new RuleBase("DANE Z WNIOSKU KREDYTOWEGO(.*?)Powód Złożenia Zapytania(;?)(?<val>[^;\\n]*?);Numer Wniosku", "Z_POWOD", RuleBase.T_MANY);
public static final RuleBase Z_TYP=new RuleBase("DANE Z WNIOSKU KREDYTOWEGO(.*?)Typ Transakcji;(?<val>[^;]*)", "Z_TYP", RuleBase.T_MANY);
public static final RuleBase Z_KWOTA=new RuleBase("DANE Z WNIOSKU KREDYTOWEGO(.*?)Kwota Kredytu(;?)(?<val>[^;\\n]*?);Limit Kredytu", "Z_KWOTA", RuleBase.T_MANY);
public static final RuleBase Z_DATA=new RuleBase("INFORMACJE O ZAPISIE Z BAZY BIOZ(.*?)Data i czas Zapytania(;?)(?<val>[^;\\n]*?);Numer", "Z_DATA", RuleBase.T_MANY);


///////////////////////////////////////
//DANE DO RACHUNKÓW
//////////////////////////////////////
public static final RuleBase R_RELACJA=new RuleBase("DANE Z UMOWY(.*?\\n){1}?Relacja Klienta Do Rachunku(;?)(?<val>.*?);Data", "R_RELACJA", RuleBase.T_MANY);
public static final RuleBase R_DATA_RELACJI=new RuleBase("Relacja Klienta Do Rachunku([^\\n]*)(\\n{1})Data Powstania Relacji;(?<val>[^\\n]*)", "R_DATA_RELACJI", RuleBase.T_MANY); //Co gdy pusta wartość
public static final RuleBase R_TYP_TRANSAKCJI=new RuleBase("Data Ustania Relacji([^\\n]*)(\\n{1})Typ Transakcji;(?<val>[^;]*)", "R_TYP_TRANSAKCJI", RuleBase.T_MANY); //co gdy pusta wartość
public static final RuleBase R_KWOTA=new RuleBase("Kwota Kredytu Z Kosztem(.*?\\n){1}Odsetkowym(.*?\\n){1}(;?)(?<val>[^;\\n]*?);Cel", "R_KWOTA", RuleBase.T_MANY);
public static final RuleBase R_WALUTA=new RuleBase("Udział Własny Kredytobiorcy(.*?\\n){1}Waluta(;?)(?<val>[^;\\n]*?);Okres", "R_WALUTA", RuleBase.T_MANY);


public static final RuleBase HISTORIA_RACHUNKU=new RuleBase("HISTORIA RACHUNKU(.*?);Waluta(?<val>.*?)INFORMACJE", "HISTORIA_RACHUNKU", RuleBase.T_ONE_TABLE);



//DANE SUMARYCZNE SZCZEGÓŁOWE
public static final RuleBase DANE_SUMARYCZNE=new RuleBase("RATALNE;LIMITY;POZOSTAŁE(?<val>.*?)UWAGI", "DANE_SUMARYCZNE", RuleBase.T_ONE_TABLE);

//KREDYTY RATALNE
public static final RuleBase KREDYTY_RATALNE=new RuleBase("KREDYTY RATALNE;KREDYTOBIORCA;PORĘCZYCIEL;PEŁNOMOCNIK(?<val>(.)*)?Najgorszy", "KREDYTY_RATALNE", RuleBase.T_ONE_TABLE);

//LIMITY ODNAWIALNE I DEBETOWE
public static final RuleBase LIMITY=new RuleBase("LIMITY ODNAWIALNE I DEBETOWE;KREDYTOBIORCA;PORĘCZYCIEL;PEŁNOMOCNIK(?<val>(.)*)?Najgorszy", "LIMITY", RuleBase.T_ONE_TABLE);

//POZOSTAŁE ZOBOWIĄZANIA
public static final RuleBase ZOBOWIAZANIA=new RuleBase("POZOSTAŁE ZOBOWIĄZANIA;KREDYTOBIORCA;PORĘCZYCIEL;PEŁNOMOCNIK(?<val>(.)*)?Najgorszy", "ZOBOWIAZANIA", RuleBase.T_ONE_TABLE);

public static final RuleBase MULTILINE=new RuleBase("Rozszerzony status przetwarzania((.|\\n)*)DANE", "TEST");
}