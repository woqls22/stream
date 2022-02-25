package com.temperature.stream.controller;

import com.temperature.stream.domain.Temperature;
import com.temperature.stream.domain.TemperatureSensor;
import com.temperature.stream.emitter.RxSseEmitter;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final TemperatureSensor temperatureSensor;
    public TemperatureController(TemperatureSensor temperatureSensor){
        this.temperatureSensor = temperatureSensor;
    }

    @GetMapping("/temperature-stream")
    public SseEmitter events(HttpServletRequest request){
        RxSseEmitter emitter = new RxSseEmitter();
        temperatureSensor.temperatureStream().subscribe(emitter.getSubscriber());
        return emitter;
    }
}
