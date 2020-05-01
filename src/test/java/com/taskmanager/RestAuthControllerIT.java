package com.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import com.taskmanager.api.client.AuthResourceClient;
import com.taskmanager.api.controller.IRestLoginController;
import com.taskmanager.service.PropertyService;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PropertyService.class)
@TestPropertySource
public class RestAuthControllerIT {

    @Autowired
    private PropertyService property;

    @Test
    public void testAuth() {
        IRestLoginController authResourceClient
                = AuthResourceClient.getInstance(property.getServer());
        String authStatus = authResourceClient.login("admin1", "admin1");
        System.out.println(authStatus);
        assertTrue(authStatus.contains("200"));
        authResourceClient.logout();
    }
}
