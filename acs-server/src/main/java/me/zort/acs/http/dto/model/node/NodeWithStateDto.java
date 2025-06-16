package me.zort.acs.http.dto.model.node;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NodeWithStateDto extends NodeDto {
    private boolean state;

    public NodeWithStateDto(String value, boolean state) {
        super(value);
        this.state = state;
    }
}
