package me.zort.acs.http.dto.body.access.grant;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class GrantNodesResponseDto {
    private Map<String, Boolean> results;

}
