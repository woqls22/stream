package com.temperature.stream;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync // 비동기 실행이 가능한 스프링 부트 어플리케이션
@SpringBootApplication
public class StreamApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(StreamApplication.class, args);
	}
	@Override
	public Executor getAsyncExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(100); // 최대 100개의 스레드풀 설정
		executor.setQueueCapacity(5);
		executor.initialize();
		return executor;
	}
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
		return new SimpleAsyncUncaughtExceptionHandler(); // 비동기 실행중 발행한 예외에 대한 예외처리
	}
}
