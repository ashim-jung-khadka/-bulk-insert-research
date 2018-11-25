package com.ashim.batch.ex;

import com.google.common.util.concurrent.Uninterruptibles;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author ashimjk on 11/24/2018
 */
public class Main {

    public static void main(String[] args) {
        final ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean("runner", Runner.class).run();
        Uninterruptibles.sleepUninterruptibly(20, TimeUnit.SECONDS);
    }
}
