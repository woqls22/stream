package com.temperature.stream.emitter;

import com.temperature.stream.domain.Temperature;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;
import rx.Subscription;

public class RxSseEmitter extends SseEmitter {
    static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Subscriber<Temperature> subscriber;

    public RxSseEmitter(){
        super(SSE_SESSION_TIMEOUT);
        this.subscriber = new Subscriber<Temperature>() {
            @Override
            public void onNext(Temperature temperature) {
                try{
                    RxSseEmitter.this.send(temperature);
                }catch (Exception e){
                    unsubscribe();
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable t) {

            }
        };
        onCompletion(subscriber::unsubscribe);
        onTimeout(subscriber::unsubscribe);
    }

    public Subscriber<Temperature> getSubscriber(){
        return subscriber;
    }
}
