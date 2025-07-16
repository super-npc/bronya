package bronya.admin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class BronyaAdminServerApp {

    public static void main(String[] args) {
        SpringApplication.run(BronyaAdminServerApp.class, args);
    }
}
