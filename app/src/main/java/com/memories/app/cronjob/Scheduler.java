package com.memories.app.cronjob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



import lombok.extern.slf4j.Slf4j;

@Component
@Async
@Slf4j
public class Scheduler {
	
	@Value("${schedule.enbled}")
	private boolean activated;
	
	@Scheduled(cron = "${schedule.cron}")
	public void scheduler() throws InterruptedException{
		if(!activated) {
			log.info("cronjob not activated");
			return;
		}
		
		log.info("cronjob start");
		
		deleteNotActivatedAccounts();
		
	}
	private void deleteNotActivatedAccounts() {
		log.info("delete not activated accounts");
	}
}
