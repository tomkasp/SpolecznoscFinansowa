package com.efsf.sf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CurrencyApi {

    private static Document getDocument(String doc) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(doc)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static String getXmlContent() {
        StringBuilder response = new StringBuilder();
        try {
            URL address = new URL("http://www.nbp.pl/kursy/xml/LastA.xml");
            HttpURLConnection conn = (HttpURLConnection) address.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            InputStream is;
            if (conn.getResponseCode() >= 400) {
                is = null;
            } else {
                is = conn.getInputStream();
            }

            if (is != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }

        

        } catch (MalformedURLException ex) {
            Logger.getLogger(CurrencyApi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CurrencyApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response.toString();
    }

    public static List<Map<String, String>> getCurrencies() throws IOException, XPathExpressionException {
        List<Map<String, String>> currencies=new ArrayList<>();
        
        Document doc = getDocument(getXmlContent());
        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xpath = xFactory.newXPath();

        // compile the XPath expression
        XPathExpression expr = xpath.compile("//pozycja/kod_waluty");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Map<String, String> map=new HashMap<>();
            map.put("kod", nodes.item(i).getTextContent());
            currencies.add(map);
        }
        
        expr = xpath.compile("//pozycja/kurs_sredni");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            currencies.get(i).put("kurs", nodes.item(i).getTextContent());
        }
        
        return currencies;
    }

}