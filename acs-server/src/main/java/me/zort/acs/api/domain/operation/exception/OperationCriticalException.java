package me.zort.acs.api.domain.operation.exception;

/**
 * Should be thrown to indicate that this operation failed critically and some follow-up repair actions
 * should be taken.
 * <p>
 * If necessary, one may wrap the cause of this error in this exception to provide more context.
 *
 * @author ZorTik
 */
public class OperationCriticalException extends RuntimeException {

  public OperationCriticalException(String message) {
    super(message);
  }

  public OperationCriticalException(String message, Throwable cause) {
    super(message, cause);
  }
}
