package me.zort.acs.domain.grant.exception;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.model.Subject;

public class InvalidGrantException extends RuntimeException {
  private final Subject accessor;
  private final Subject accessed;
  private final RightsHolder rightsHolder;

  public InvalidGrantException(
          Subject accessor, Subject accessed, RightsHolder rightsHolder, IllegalArgumentException cause) {
    super("Invalid grant creation request!", cause);
    this.accessor = accessor;
    this.accessed = accessed;
    this.rightsHolder = rightsHolder;
  }
}
