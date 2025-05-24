package me.zort.acs.api.domain.access;

import me.zort.acs.domain.model.Subject;

import java.util.List;

public interface RightsNegotiationService {

    List<RightsHolder> getRightsHolders(Subject accessor, Subject accessed);
}
