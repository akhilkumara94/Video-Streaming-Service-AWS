package com.akhil.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class, scanBasePackages="com.akhil.video")
@EnableJpaRepositories(basePackages = "com.akhil.video.dao")
public class VideoServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(VideoServiceApplication.class, args);
		System.out.println("--server started--");
	}
}
