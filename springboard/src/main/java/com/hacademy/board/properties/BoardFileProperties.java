package com.hacademy.board.properties;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "board.file")
public class BoardFileProperties {
	//application.properties에서 제공받을 정보
	private String upload, image;
	
	private File uploadPath, imagePath;
	
	@PostConstruct
	public void init() {
		//os 상관없이 공통 적용 가능한 홈 경로 지정(프로젝트 외부로 지정)
		uploadPath = new File(System.getProperty("user.home"), upload);
		imagePath = new File(uploadPath, image);
	}
}
