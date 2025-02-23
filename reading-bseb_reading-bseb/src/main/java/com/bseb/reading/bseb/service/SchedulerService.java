package com.bseb.reading.bseb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Autowired
    private BSEDataChecker bseDataChecker;

    @Autowired
    private EmailService emailService;
    
    private int prevRecord=0;

   // @Scheduled(cron = "0 0 9 * * ?") // Runs every day at 9 AM
    @Scheduled(fixedDelay = 20000)
    public void checkAndNotify() {
        String fromDate = "01/31/2025"; // Change dynamically
        String toDate = "01/31/2025";

        int curNoOfRec = BSEDataChecker.checkCorporateActions(fromDate, toDate);
        if (curNoOfRec>prevRecord) {
            /*emailService.sendNotification(
                "recipient@example.com",
                "BSE Corporate Action Alert",
                "There is new corporate action data available for " + fromDate
            );*/
        	//prevRecord = curNoOfRec;
        	emailService.sendEmail("xpkunal@gmail.com", "BSE Announcements", "Hi Ayush Congratulations! Total Announcements: " + curNoOfRec);
        	//emailService.sendEmail("ankeshaman510@gmail.com", "BSE Announcements", "Hi Ayush Congratulations! Total Announcements: " + curNoOfRec);
        	prevRecord = curNoOfRec;
        }
    }
}

