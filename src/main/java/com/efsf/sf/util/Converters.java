package com.efsf.sf.util;

import java.io.Serializable;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.Years;

/**
 *
 * @author XaI
 */
public class Converters implements Serializable
{
    public int ageFromBirthDate(Date birthDate)
    {
        return Years.yearsBetween(new LocalDate(birthDate), new LocalDate()).getYears();
    }
    
    public String hoursLeft(Date end)
    {
        return Hours.hoursBetween(new DateTime(), new DateTime(end)).getHours() + " h";
    }
    

    

    
}
