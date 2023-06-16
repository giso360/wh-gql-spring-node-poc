package wh.com.gqlwhclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class GqlWhClientApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GqlWhClientApplication.class, args);
		new TestRunner().run();
	}

}
