package com.temperature.stream.controller;

import com.temperature.stream.domain.Temperature;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
public class TemperatureController {
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping(value = "/temperature-stream") // 요청 핸들러
    public SseEmitter events(HttpServletRequest request){
        SseEmitter emitter = new SseEmitter();
        clients.add(emitter);

        emitter.onTimeout(()->clients.remove(emitter));
        emitter.onCompletion(()->clients.remove(emitter));
        return emitter; // 클라이언트 요청 시 새로운 SseEmitter를 생성하여 클라이언트 목록에 등록과 동시에 이를 반환
    }
    @Async // 메서드를 비동기 실행한다. 별도로 구성된 스레드 풀에서 호출됨
    @EventListener // 스프링으로부터 이벤트를 수신하기 위한 어노테이션. 온도 이벤트를 수신할 때 handleMsg 호출
    public void handleMessage(Temperature temperature){
        List<SseEmitter> deadEmitters = new ArrayList<>();
        for (SseEmitter emitter : clients){
            try{
                emitter.send(temperature, MediaType.APPLICATION_JSON); // 각 이벤트들에게 병렬로 메시지 전송
            }catch (Exception ignore){
                deadEmitters.add(emitter);
            }
        }
        clients.removeAll(deadEmitters); // 활성 클라이언트에서 전송 실패한 연결 제거
    }
}
