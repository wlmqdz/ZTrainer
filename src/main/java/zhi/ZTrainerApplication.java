package zhi;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("zhi")
public class ZTrainerApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(ZTrainerApplication.class, args);
		
		CamelContext camelContext = ctx.getBean(CamelContext.class);
		camelContext.startRoute("importMovie");
		camelContext.startRoute("importRating");
		
		CamelSpringBootApplicationController applicationController = ctx.getBean(CamelSpringBootApplicationController.class);
		applicationController.blockMainThread();
	}
}
