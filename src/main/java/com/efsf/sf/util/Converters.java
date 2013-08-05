/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.Hours;

/**
 *
 * @author XaI
 */
public class Converters 
{
    public int ageFromBirthDate(Date birthDate)
    {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        
        int age;
        int factor = 0;
        
        Calendar calBirth = new GregorianCalendar();
        calBirth.setTime(birthDate);
        if(cal.get(Calendar.DAY_OF_YEAR) < calBirth.get(Calendar.DAY_OF_YEAR)) {
            factor = -1; 
            
        }
        age = cal.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR) + factor;

        return age;
    }
    
    public String hoursLeft(Date end)
    {
        return Hours.hoursBetween(new DateTime(), new DateTime(end)).getHours() + " h";
    }
    

    

    
}
