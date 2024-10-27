package fouragrant.scentasy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		servers = {
				@Server(url = "/", description = "Default Server url")
		}
)
@SpringBootApplication
public class ScentasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScentasyApplication.class, args);
	}

}
