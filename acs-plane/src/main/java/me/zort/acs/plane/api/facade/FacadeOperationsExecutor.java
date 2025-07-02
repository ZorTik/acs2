package me.zort.acs.plane.api.facade;

import org.springframework.ui.Model;

public interface FacadeOperationsExecutor {

    /**
     * Executes the given operation without returning a result.
     *
     * @param operation the operation to execute
     */
    void execute(FacadeExecuteOperation operation);

    /**
     * Executes the given operation with the provided model, without returning a result.
     *
     * @param operation the operation to execute
     * @param model the model to pass to the operation
     */
    void execute(FacadeExecuteOperation operation, Model model);

    /**
     * Executes the given supply operation and returns its result.
     *
     * @param operation the operation to execute
     * @param <T> the type of the result
     * @return the result of the operation
     */
    <T> T supply(FacadeSupplyOperation<T> operation);

    /**
     * Executes the given supply operation with the provided model and returns its result.
     *
     * @param operation the operation to execute
     * @param model the model to pass to the operation
     * @param <T> the type of the result
     * @return the result of the operation
     */
    <T> T supply(FacadeSupplyOperation<T> operation, Model model);
}
