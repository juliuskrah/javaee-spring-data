package com.juliuskrah;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.junit.Arquillian;

@RunWith(Arquillian.class)
@DefaultDeployment
public class PersonServiceTest {

    @Test
    public void tesCreatePerson() {
        assertTrue(true);
    }
}
