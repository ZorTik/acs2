package me.zort.acs.domain.garbage;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.garbage.disposable.Disposable;
import me.zort.acs.api.domain.garbage.DisposablesHolder;
import me.zort.acs.api.domain.garbage.ResourceDisposalRegistry;
import me.zort.acs.api.domain.garbage.ResourceDisposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class ResourceDisposalServiceImpl implements ResourceDisposalService {
    private final ResourceDisposalRegistry registry;
    private final ApplicationContext context;

    public void dispose(DisposablesHolder disposablesHolder) {
        disposablesHolder.getDisposables().forEach(this::dispose);
    }

    public void dispose(Disposable disposable) {
        registry.getDisposalFor(disposable).disposeIfNecessary(disposable);
    }

    @Override
    public <T extends Disposable> void disposeBeans(Class<T> beanClass) {
        context.getBeansOfType(beanClass).forEach((key, bean) -> dispose(bean));
    }
}
