package org.bear.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String s) {

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }

    }
}
