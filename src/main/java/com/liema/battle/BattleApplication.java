package com.liema.battle;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ImportAutoConfiguration(RedissonAutoConfiguration.class)
@SpringBootApplication
public class BattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleApplication.class, args);
	}

}
