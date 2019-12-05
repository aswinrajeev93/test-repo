package com.mes.soa.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.codeguruprofilerjavaagent.Profiler;
@SpringBootApplication
public class SoaDataBatchInformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoaDataBatchInformationApplication.class, args);
		new Profiler.Builder()
   .profilingGroupName("test-Deployment")
   .build().start()
	}

}
