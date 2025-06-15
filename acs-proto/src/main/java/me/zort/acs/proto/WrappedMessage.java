package me.zort.acs.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WrappedMessage<C> {
    private String key;
    private C content;

}
