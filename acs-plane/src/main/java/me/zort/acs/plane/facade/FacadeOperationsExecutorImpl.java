package me.zort.acs.plane.facade;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.facade.FacadeExecuteOperation;
import me.zort.acs.plane.api.facade.FacadeOperationsExecutor;
import me.zort.acs.plane.api.facade.FacadeSupplyOperation;
import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.api.http.error.HttpErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class FacadeOperationsExecutorImpl implements FacadeOperationsExecutor {
    private final HttpErrorService errorService;

    @Override
    public void execute(FacadeExecuteOperation operation) {
        try {
            operation.execute();
        } catch (HttpError e) {
            errorService.propagate(e);
        }
    }

    @Override
    public void execute(FacadeExecuteOperation operation, Model model) {
        try {
            operation.execute();
        } catch (HttpError e) {
            errorService.propagate(e, model);
        }
    }

    @Override
    public <T> T supply(FacadeSupplyOperation<T> operation) {
        try {
            return operation.get();
        } catch (HttpError e) {
            errorService.propagate(e);

            return null;
        }
    }

    @Override
    public <T> T supply(FacadeSupplyOperation<T> operation, Model model) {
        try {
            return operation.get();
        } catch (HttpError e) {
            errorService.propagate(e, model);

            return null;
        }
    }
}
