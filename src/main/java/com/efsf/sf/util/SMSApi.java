package com.efsf.sf.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class SMSApi {

    private static String url = "https://ssl.smsapi.pl/sms.do?username=adam.nowak7@o2.pl&password=05a671c66aefea124cc08b76ea6d30bb&from=Eco";

    //return stattus 
    //   0: succcess 
    //   less 0 - error code
    public static int sendSms(String number, String message) {
        try {
            if (valid(number) && valid(message)) {
                URL address = new URL(url + "&to=" + number + "&message=" + message.replaceAll(" ", "+"));
                HttpsURLConnection conn = (HttpsURLConnection) address.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
                
                InputStream is;
                if (conn.getResponseCode() >= 400) {
                    is = conn.getErrorStream();
                } else {
                    is = conn.getInputStream();
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String result = response.toString();
                System.out.println(result);
                if (result.startsWith("ERROR:")) {
                    return 0 - Integer.parseInt(result.substring(6));
                } else if (result.startsWith("OK:")) {
                    return 0;
                } else {
                    return -1000; //Unknow error
                }

            } else {
                return -1001;  //not walid phone and message
            }

        } catch (Exception e) {
            Logger.getLogger(SMSApi.class.getName()).log(Level.SEVERE, null, e);
            return -1002; //other exception
        }
    }

    private static boolean valid(String text) {
        return text != null && !text.equals("");
    }
}
