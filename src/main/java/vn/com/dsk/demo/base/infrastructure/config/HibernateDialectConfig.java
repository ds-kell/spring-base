package vn.com.dsk.demo.base.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HibernateDialectConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> {
            String dbUrl;

            if (dataSource instanceof HikariDataSource hikariDataSource) {
                dbUrl = hikariDataSource.getJdbcUrl();
            } else {
                throw new UnsupportedOperationException("Unsupported DataSource type: " + dataSource.getClass().getName());
            }

            if (dbUrl.contains("mysql")) {
                hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            } else if (dbUrl.contains("postgresql")) {
                hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            } else {
                throw new UnsupportedOperationException("Database type not supported: " + dbUrl);
            }
        };
    }
}