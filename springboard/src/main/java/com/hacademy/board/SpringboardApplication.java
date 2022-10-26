package com.hacademy.board;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling//스케쥴러 허용 설정
@SpringBootApplication
public class SpringboardApplication {

	public static void main(String[] args) {
		generateDatabaseFile();
		SpringApplication.run(SpringboardApplication.class, args);
	}
	
	public static void generateDatabaseFile() {
		File userDirectory = new File(System.getProperty("user.home"));
		File target = new File(userDirectory, "test.mv.db");
		if(!target.exists()) {
			try {
				if(target.createNewFile()) {
					log.info("데이터베이스 생성 성공");
				}
				else {
					log.info("이미 데이터베이스가 생성되어 있음");
				}
			}
			catch(Exception e) {
				log.error("데이터베이스 생성 실패", e);
			}
		}
	}

}
