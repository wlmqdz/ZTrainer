package zhi;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("zhi")
@ImportResource({ "classpath:batch.xml" })
public class ZTrainerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZTrainerApplication.class, args);
	}
}
