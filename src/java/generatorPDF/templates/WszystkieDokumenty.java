/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generatorPDF.templates;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfStamper;
import generatorPDF.core.GeneratorPDF;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.dao.KredytyDao;
import sql.entity.Klienci;
import sql.entity.KlienciKredyty;
import sql.entity.Kredyty;

/**
 *
 * @author WR1EI1
 */
public class WszystkieDokumenty {

    public int wypelnij(PdfStamper pdfStamper, int idKredytu) {

        int idDokumentu = 0;
        KredytyDao kredytyDAO = new KredytyDao();
        KlienciKredyty kk = kredytyDAO.readKlienciKredyty(idKredytu);
        Klienci klient = kk.getKlienci();
        Kredyty kredyt = kk.getKredyty();
        Klienci partner = kredytyDAO.readPartner(idKredytu);

        try {
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(1);//pierwsza stronka
            content.beginText();
            content.setFontAndSize(bf, 12);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 255, 573, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 30, 740, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 30, 725, 0);

            if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 695, 0);
            } else {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 695, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 680, 0);
            }


            if (partner != null) {

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getImie() + " " + partner.getNazwisko(), 30, 660, 0);

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getUlica() + " " + partner.getNrDomu() + "/" + partner.getNrMieszkania(), 30, 645, 0);

                if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getKodPocztowy() + " " + partner.getPoczta(), 30, 630, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + partner.getPesel(), 30, 615, 0);
                } else {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getMiejscowosc(), 30, 630, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getKodPocztowy() + " " + partner.getPoczta(), 30, 615, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + partner.getPesel(), 30, 600, 0);
                }

            }


            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataMozliwegoUruchomienia().toString(), 255, 525, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNazwaBanku(), 255, 500, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKredytuBrutto().toString() + " zł", 170, 460, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getProwizjaBankuWpln().toString() + " zł", 170, 445, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getUbezpieczenieWpln().toString() + " zł", 170, 430, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKosztaWpln().toString() + " zł", 170, 415, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() + " zł", 170, 400, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKonsolidacji().toString() + " zł", 170, 385, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOkresKredytowaniaWmc().toString(), 360, 445, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getRataWpln().toString() + "zł", 360, 430, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOprocentowanieWprocentach().toString() + " %", 360, 415, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getWolnaGotowka().toString() + " zł", 360, 400, 0);

            content.endText();

        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(2);//pierwsza stronka
            content.beginText();
            content.setFontAndSize(bf, 12);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() + ", dn. " + kredyt.getDataDodaniaKredytu().toString(), 365, 816, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 250, 588, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString(), 100, 574, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() + " zł", 300, 574, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 25, 383, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 360, 383, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy(), 125, 353, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 325, 353, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getSeriaDowodu(), 240, 322, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getNrDowodu(), 360, 322, 0);

            content.endText();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(3);//pierwsza stronka
            content.beginText();
            content.setFontAndSize(bf, 12);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() + ", dn. " + kredyt.getDataDodaniaKredytu().toString(), 365, 740, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 30, 740, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 30, 725, 0);


            if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 695, 0);
            } else {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 695, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 680, 0);
            }

            if (partner != null) {

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getImie() + " " + partner.getNazwisko(), 30, 660, 0);

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getUlica() + " " + partner.getNrDomu() + "/" + partner.getNrMieszkania(), 30, 645, 0);

                if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getKodPocztowy() + " " + partner.getPoczta(), 30, 630, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + partner.getPesel(), 30, 615, 0);
                } else {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getMiejscowosc(), 30, 630, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getKodPocztowy() + " " + partner.getPoczta(), 30, 615, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + partner.getPesel(), 30, 600, 0);
                }

            }


            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNazwaBanku(), 450, 514, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 25, 485, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString(), 183, 485, 0);

            content.endText();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }




        try {

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(4);//pierwsza stronka
            content.beginText();
            content.setFontAndSize(bf, 12);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() + ", dn. " + kredyt.getDataDodaniaKredytu().toString(), 365, 740, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 30, 740, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 30, 725, 0);


            if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 695, 0);
            } else {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 30, 710, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 695, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 680, 0);
            }

            if (partner != null) {

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getImie() + " " + partner.getNazwisko(), 30, 660, 0);

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getUlica() + " " + partner.getNrDomu() + "/" + partner.getNrMieszkania(), 30, 645, 0);

                if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getKodPocztowy() + " " + partner.getPoczta(), 30, 630, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + partner.getPesel(), 30, 615, 0);
                } else {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getMiejscowosc(), 30, 630, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getKodPocztowy() + " " + partner.getPoczta(), 30, 615, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + partner.getPesel(), 30, 600, 0);
                }

            }

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 370, 513, 0);


            content.endText();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }



        try {

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(5);//pierwsza stronka
            content.beginText();

            content.setFontAndSize(bf, 17);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "P." + kredyt.getNrUmowyPosrednictwa(), 337, 674, 0);

            content.setFontAndSize(bf, 12);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getMiejscePodpisaniaDokumentow() + ", dn. " + kredyt.getDataDodaniaKredytu().toString(), 365, 780, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 30, 780, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 30, 765, 0);


            if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 750, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 735, 0);
            } else {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 30, 750, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 735, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 720, 0);
            }

            //if (partner != null) {

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 30, 620, 0);

                content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 30, 605, 0);

                if (klient.getMiejscowosc().equals(klient.getPoczta())) {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 590, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 575, 0);
                } else {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 30, 590, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 30, 575, 0);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "PESEL: " + klient.getPesel(), 30, 560, 0);
                }

            //}

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 100, 474, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString(), 330, 474, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() + " zł", 60, 430, 0);

            Calendar c = Calendar.getInstance();
            c.setTime(kredyt.getDataDodaniaKredytu());
            c.add(Calendar.DATE, 7);
            Date plus7dni = c.getTime();
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, dt1.format(plus7dni), 400, 415, 0);

            content.endText();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(6);//pierwsza stronka
            content.beginText();
            content.setFontAndSize(bf, 12);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() + " zł", 330, 615, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 110, 565, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta() + " " + klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 110, 545, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 170, 520, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() + " zł", 330, 318, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 110, 269, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta() + " " + klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 110, 248, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 170, 223, 0);

            content.endText();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(7);//pierwsza stronka
            
            content.beginText();
            content.setFontAndSize(bf, 10);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 385, 816, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString(), 80, 783, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getImie() + " " + klient.getNazwisko(), 80, 773, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getMiejscowosc(), 380, 773, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getKodPocztowy() + " " + klient.getPoczta(), 80, 761, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getUlica() + " " + klient.getNrDomu() + "/" + klient.getNrMieszkania(), 300, 761, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getSeriaDowodu(), 210, 750, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getNrDowodu(), 280, 750, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, klient.getPesel(), 400, 750, 0);

            if (partner != null) {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getImie() + " " + partner.getNazwisko(), 80, 725, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getMiejscowosc(), 380, 725, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getKodPocztowy() + " " + partner.getPoczta(), 80, 712, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getUlica() + " " + partner.getNrDomu() + "/" + partner.getNrMieszkania(), 300, 712, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getSeriaDowodu(), 210, 698, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getNrDowodu(), 280, 698, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, partner.getPesel(), 400, 698, 0);
                System.out.println("NIE kwadrat!");
                
            } 

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() + " zł", 318, 361, 0);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getDataDodaniaKredytu().toString(), 193, 155, 0);
            content.endText();
            
             System.out.println("TESTYYY 1");
            if(partner==null){
                System.out.println("TESTYYY 2");
                content.setColorStroke(BaseColor.WHITE);
                content.setColorFill(BaseColor.WHITE);

                content.closePathStroke();
                content.moveTo(10, 743);
                content.lineTo(600, 743);
                content.lineTo(600, 685);
                content.lineTo(10, 685);

                content.fill();
                
                content.setColorStroke(BaseColor.BLACK);
                content.setColorFill(BaseColor.BLACK);
            
                content.beginText();
                content.setFontAndSize(bf, 12);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Zwany dalej zleceniodawcą:", 19, 688, 0);
                content.endText();
                
            }

        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }



        try {

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);

            PdfContentByte content = pdfStamper.getOverContent(8);//pierwsza stronka
            content.beginText();

            content.setFontAndSize(bf, 13);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNazwaBanku(), 353, 760, 0);

            content.setFontAndSize(bf, 11);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getNrUmowyPosrednictwa(), 275, 704, 0);

            content.setFontAndSize(bf, 14);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKredytuBrutto().toString() + " zł", 270, 660, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKwotaKonsolidacji().toString() + " zł", 270, 625, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getWolnaGotowka().toString() + " zł", 270, 590, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getRataWpln().toString() + " zł", 270, 555, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOkresKredytowaniaWmc().toString() + " Miesięcy", 270, 520, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getKosztaWpln().toString() + " zł", 270, 485, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getProwizjaBankuWpln().toString() + " zł", 270, 450, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getUbezpieczenieWpln().toString() + " zł", 270, 415, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "0", 270, 380, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getOprocentowanieWprocentach().toString() + " %", 270, 345, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, kredyt.getSwotWpln().toString() + " zł", 270, 310, 0);


            content.endText();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneratorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }



        kredyt.setCzyWygenerowanoDokumenty(Boolean.TRUE);
        kredytyDAO.updateKredyty(kredyt);



        return idDokumentu;
    }
}
