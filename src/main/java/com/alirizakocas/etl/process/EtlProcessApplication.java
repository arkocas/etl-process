package com.alirizakocas.etl.process;

import com.alirizakocas.etl.process.service.EtlProcessService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EtlProcessApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(EtlProcessApplication.class, args);

		EtlProcessService service = applicationContext.getBean(EtlProcessService.class);

		service.publishProducts();
		service.publishBestSellers();
	}

}
