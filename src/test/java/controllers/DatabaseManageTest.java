package controllers;

import models.Containers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseManageTest {

    private DatabaseManage db = new DatabaseManage();
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void handleNewRequest() {
        Containers actual = db.handleNewRequest("1500","5","Feher","Feher1500");
        Containers expected = Containers.builder()
                .Amount(1500)
                .Container(5)
                .Type("Feher")
                .WineId("Feher1500")
                .build();

                expected.setId(actual.getId());
        Assert.assertEquals(expected,actual);
    }
}