package me.zort.acs.api.messaging.listener;

public interface ConvertedMessageListener<T> {

    void onMessage(T message);

    String getChannel();

    Class<T> getMessageType();
}
