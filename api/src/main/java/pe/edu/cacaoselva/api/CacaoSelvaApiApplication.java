package pe.edu.cacaoselva.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = "pe.edu.cacaoselva"
)
@EnableJpaRepositories(
        basePackages =
            "pe.edu.cacaoselva.infrastructure.persistence"
)
@EntityScan(
        basePackages =
            "pe.edu.cacaoselva.infrastructure.persistence"
)
public class CacaoSelvaApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                CacaoSelvaApiApplication.class,
                args
        );
    }
}