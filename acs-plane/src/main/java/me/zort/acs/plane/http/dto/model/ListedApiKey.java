package me.zort.acs.plane.http.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ListedApiKey {
    private final int id;
    private final String name;
    private final List<ListedPrivilege> claims;

}
