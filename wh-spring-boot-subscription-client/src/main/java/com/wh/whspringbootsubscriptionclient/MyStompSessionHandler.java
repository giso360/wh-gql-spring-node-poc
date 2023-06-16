package com.wh.whspringbootsubscriptionclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

@Slf4j
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, @Nullable Object payload){
        log.info(payload.toString());
    }


}
