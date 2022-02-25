package com.temperature.stream.domain;

import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component // 스프링 빈으로 등록
public class TemperatureSensor {
    private final Random random = new Random();

    private final Observable<Temperature> dataStream =
        Observable
                .range(0, Integer.MAX_VALUE) // 팩토리메서드 ; 무한대의 스트림 생성
                .concatMap(tick -> Observable  // concatMap : tick객체를 수신하여 Observable스트림 변환, 함수를 적용한다음 결과 스트림에 결합
                        .just(tick)
                        .delay(random.nextInt(5000),TimeUnit.MILLISECONDS) // 임의의 지연 후
                        .map(tickValue->this.probe())) // tickValue에 probe메서드를 호출, 결합 (온도 데이터 수신)
                .publish().refCount();
    private Temperature probe(){
        return new Temperature(16+random.nextGaussian()*10);
    }
    public Observable<Temperature> temperatureStream(){ // 데이터 스트림 반환
        return dataStream;
    }
}
