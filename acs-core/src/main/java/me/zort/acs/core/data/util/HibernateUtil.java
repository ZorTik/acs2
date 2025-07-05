package me.zort.acs.core.data.util;

import lombok.experimental.UtilityClass;
import me.zort.acs.core.data.entity.AcsEntity;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@UtilityClass
public final class HibernateUtil {

    public static boolean equals(Object thisObj, Object thatObj) {
        if (!(thisObj instanceof AcsEntity<?> thisEntity)) {
            throw new IllegalArgumentException("thisObj must be an instance of AcsEntity");
        }
        if (!(thatObj instanceof AcsEntity<?> thatEntity)) {
            return false;
        }

        if (thisObj == thatObj) {
            return true;
        }

        Class<?> oEffectiveClass = thatObj instanceof HibernateProxy
                ? ((HibernateProxy) thatObj).getHibernateLazyInitializer().getPersistentClass() : thatObj.getClass();
        Class<?> thisEffectiveClass = thisObj instanceof HibernateProxy
                ? ((HibernateProxy) thisObj).getHibernateLazyInitializer().getPersistentClass() : thisObj.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }

        return thisEntity.getId() != null && Objects.equals(thisEntity.getId(), thatEntity.getId());
    }

    public static int hashCode(AcsEntity<?> entity, boolean hasEmbeddableKey) {
        if (entity == null) {
            return 0;
        }

        if (hasEmbeddableKey) {
            return Objects.hash(entity.getId());
        } else {
            return entity instanceof HibernateProxy
                    ? ((HibernateProxy) entity).getHibernateLazyInitializer().getPersistentClass().hashCode()
                    : entity.getClass().hashCode();
        }
    }
}
