package org.bear.admin.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSlf4j {

    private Logger LOGGER=LoggerFactory.getLogger(TestSlf4j.class);


    @Test
    public void add(){

        LOGGER.info("info--------------");
        LOGGER.error("error------------");
        LOGGER.debug("debug------------");
        LOGGER.warn("warn---------------");
    }

}
