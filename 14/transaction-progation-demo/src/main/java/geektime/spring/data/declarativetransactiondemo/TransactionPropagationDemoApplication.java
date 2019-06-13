package geektime.spring.data.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Slf4j
public class TransactionPropagationDemoApplication implements CommandLineRunner {
	@Autowired
	private FooService fooService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(TransactionPropagationDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			fooService.insertThenRollback();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='BBB'", Long.class));
		}

		try {
			fooService.invokeInsertThenRollback();
		} catch (Exception e) {
			log.info("AAA" +
							" {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='AAA'", Long.class));
		}
	}
}

