package me.zort.acs.http.dto.model.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NodeWithStateDto extends NodeDto {
    private String value;
    private boolean state;

}
