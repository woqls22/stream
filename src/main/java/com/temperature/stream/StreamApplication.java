package com.temperature.stream;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

// @EventListener애노테이션을 사용하지 않고, @Async에 대한 의존성이없어짐.
@SpringBootApplication
public class StreamApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(StreamApplication.class, args);
	}
}
