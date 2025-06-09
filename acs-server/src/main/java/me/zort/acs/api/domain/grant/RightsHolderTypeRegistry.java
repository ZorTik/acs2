package me.zort.acs.api.domain.grant;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.access.rights.type.RightsHolderType;

import java.util.Set;
import java.util.function.BiFunction;

public interface RightsHolderTypeRegistry {

    /**
     * Finds the appropriate RightsHolderType for the given RightsHolder,
     * casts it to be able to call the RightsHolderType-specific function and
     * applies the provided call function to the rights holder type.
     * <p></p>
     * This hereby selects the correct RightsHolderType based on the rights holder,
     * or throws an IllegalArgumentException if the type is unsupported.
     *
     * @param holder The RightsHolder to cast.
     * @param callFunc The function to call with the cast holder and its type.
     * @param <T> The type of RightsHolder.
     * @param <R> The return type of the function.
     * @return The result of the function call.
     */
    <T extends RightsHolder, R> R castAndCallAdapter(
            RightsHolder holder, BiFunction<T, RightsHolderType<T>, R> callFunc);

    /**
     * Returns a set of all supported RightsHolder types, which means
     * all supported class types that inherit from RightsHolder and are accepted
     * as such by the system.
     *
     * @return A set of supported RightsHolder class types.
     */
    Set<Class<?>> getSupportedHolderTypes();

    default void requireSupportedType(RightsHolder rightsHolder) {
        Class<? extends RightsHolder> classOfRightsHolder = rightsHolder.getClass();

        if (!getSupportedHolderTypes().contains(classOfRightsHolder)) {
            throw new IllegalArgumentException("Invalid rights holder: " + classOfRightsHolder.getSimpleName());
        }
    }
}
