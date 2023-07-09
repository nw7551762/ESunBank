package esun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import esun.user.Test;

@SpringBootApplication
@EnableAspectJAutoProxy
@PropertySource(value = {"classpath:jdbc.properties"},ignoreResourceNotFound = true)
public class EsunBankApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(EsunBankApplication.class, args);
	}
}



