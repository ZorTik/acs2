package me.zort.acs_plane.domain.definitions.event;

import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.event.AbstractRealmEvent;
import org.jetbrains.annotations.Nullable;

@Getter
public class DefinitionsSavedEvent extends AbstractRealmEvent {
    @Nullable
    private final DefinitionsModel definitions;

    public DefinitionsSavedEvent(Realm realm, @Nullable DefinitionsModel definitions) {
        super(realm);
        this.definitions = definitions;
    }
}
