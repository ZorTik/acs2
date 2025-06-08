package me.zort.acs.client.test;

import me.zort.acs.client.AbstractAcsClient;
import me.zort.acs.client.AcsClientBuilder;
import me.zort.acs.client.http.adapter.HttpAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AcsClientBuilderTests extends BaseTestCase {
    private AcsClientBuilder builder;

    private static class TestClientBuilder extends AcsClientBuilder<AbstractAcsClient> {
        @Override
        protected AbstractAcsClient doBuild() {
            return mock(AbstractAcsClient.class);
        }
    }

    @BeforeEach
    public void setUp() {
        builder = new TestClientBuilder();
    }

    @Test
    public void testNotEnoughSettings1() {
        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    public void testNotEnoughSettings2() {
        builder = builder.withBaseUrl("");
        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    public void testNotNulls() {
        assertThrows(NullPointerException.class, () -> builder.withHttpAdapter(null));
        assertThrows(NullPointerException.class, () -> builder.withBaseUrl(null));
        assertThrows(NullPointerException.class, () -> builder.withHttpSerializer(null));
    }

    @Test
    public void testBuilds() {
        builder = builder
                .withBaseUrl("")
                .withHttpAdapter(mock(HttpAdapter.class));
        builder.build();
    }
}
