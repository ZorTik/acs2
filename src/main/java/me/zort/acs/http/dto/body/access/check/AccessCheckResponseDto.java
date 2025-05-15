package me.zort.acs.http.dto.body.access.check;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class AccessCheckResponseDto {
    private Map<String, Boolean> states;

}
