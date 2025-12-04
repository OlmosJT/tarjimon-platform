package io.olmosjt.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "io.olmosjt.auth",
        "io.olmosjt.common.entity"
})
@ComponentScan(basePackages = {
        "io.olmosjt.auth",
        "io.olmosjt.common"
})
// 3. Ensure Repositories are scanned (usually defaults to current package, but safe to add)
@EnableJpaRepositories(basePackages = "io.olmosjt.auth.domain.repository")
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
