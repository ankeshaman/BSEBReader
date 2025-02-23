package com.bseb.reading.bseb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bseb.reading.bseb.service.BSEDataChecker;
import com.bseb.reading.bseb.service.SchedulerService;

@SpringBootApplication
@EnableScheduling  // This enables scheduled tasks
public class ReadingBsebApplication {
    
	
	public static void main(String[] args) {
		SpringApplication.run(ReadingBsebApplication.class, args);
		//String fromDate = "01/31/2025"; // Change dynamically
       // String toDate = "01/31/2025";
		//BSEDataChecker bseDataChecker = new BSEDataChecker();
		//bseDataChecker.checkCorporateActions(fromDate, toDate);
        
	}
	
	/*@Bean
    CommandLineRunner run(BSEDataChecker scraper) {
        return args -> scraper.checkCorporateActions("01/31/2025","01/31/2025");
    }*/
	
	/*@Bean
    CommandLineRunner run(SchedulerService scraper) {
        return args -> scraper.checkAndNotify();
    }*/


}
