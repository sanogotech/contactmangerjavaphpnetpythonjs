package com.macrosoftas.contactmanager.tools;



import javax.sql.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionPoolIntegrationTest {
     
    @Autowired
    private DataSource dataSource;
     
    @Test
    public void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
        assertNotEquals("org.apache.tomcat.jdbc.pool.DataSource",dataSource.getClass().getName());
    }
	
	
	@Test
    public void hikariConnectionPoolIsConfigured() {
        assertEquals("com.zaxxer.hikari.HikariDataSource", dataSource.getClass().getName());
    }
}