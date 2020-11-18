package com.smalik.esdatafeeder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
public class EsDataFeederApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EsDataFeederApplication.class, args);
	}

	private Config config;
	private EsService service;

	public void run(String... args) throws Exception {

		long starTime = System.currentTimeMillis();

		// create the index if it doesn't exist
		service.createIndex();

		// add documents in batches
		for (int x = 0; x < config.getIndexTotalDocuments(); x = x + config.getIndexBatchSize()) {
			service.addDocumentsInBulk(x);
		}

		log.info("DONE: Documents={}, Time={}",
				config.getIndexTotalDocuments(),
				System.currentTimeMillis()-starTime);

		// close the client
		service.close();
	}

}
