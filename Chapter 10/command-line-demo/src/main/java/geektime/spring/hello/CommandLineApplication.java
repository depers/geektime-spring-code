package geektime.spring.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CommandLineApplication {

	public static void main(String[] args) {
		// 法一：如果 application.properties中没有spring.main.web-application-type=none，则需这样配置
//		new SpringApplicationBuilder(CommandLineApplication.class)
//				.web(WebApplicationType.NONE)
//				.run(args);
		// 法二：根据 application.properties 里的配置来决定 WebApplicationType
		SpringApplication.run(CommandLineApplication.class, args);
	}
}
