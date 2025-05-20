package me.zort.acs.http.dto.body.access.revoke;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class RevokeNodesResponseDto {
    private Map<String, Boolean> results;

}
