package com.rvg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-check")
    public String checkDb() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            return "Connected to: " + conn.getMetaData().getDatabaseProductName()
                    + " version " + conn.getMetaData().getDatabaseProductVersion();
        }
    }
}
