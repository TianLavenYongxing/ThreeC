package com.threec.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTest {
    @Autowired
    JwtService jwtService;

    @Test
    public void TestExtractUsername() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMYXZlbiIsImlhdCI6MTcyMTYzNjU1NiwiZXhwIjoxNzIxNzIyOTU2fQ.VuCle9QsKagsTBb8ABieqxZ4mvS8VVc4p9XoRkxYAhA";
        String username = jwtService.extractUsername(jwt);
        System.out.println(username);
    }
}
