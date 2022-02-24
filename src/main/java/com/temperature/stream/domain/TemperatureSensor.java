package com.temperature.stream.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TemperatureSensor {
    private final ApplicationEventPublisher publisher;
    private final Random rnd = new Random(); // 난수 생성
    // 이벤트 생성 프로세스는 별도 스레드에서 발생
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public TemperatureSensor(ApplicationEventPublisher publisher){
        this.publisher =publisher;
    }

    @PostConstruct
    public void startProcessing(){ // 빈이 생성될 때 호출되어 온도 시나리오의 전체 시퀀스를 주입
        this.executor.schedule(this::probe, 1, TimeUnit.SECONDS);
    }
    private void probe(){
        double temperature = 16+rnd.nextGaussian()*10;
        publisher.publishEvent(new Temperature(temperature)); // event발행

        // 랜덤한 지연시간을 두고 다음 읽기 스케쥴 예약
        executor.schedule(this::probe, rnd.nextInt(5000), TimeUnit.MILLISECONDS);
    }
}
