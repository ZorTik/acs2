package me.zort.acs.api.common;

import me.zort.acs.client.http.model.Subject;
import me.zort.acs.client.v1.AcsClientV1;

public interface SubjectProvider {

    Subject getSubject(AcsClientV1 client);
}
