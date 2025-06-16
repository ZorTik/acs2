package me.zort.acs.proto;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;

@UtilityClass
public final class MessageTypeEntries {

    /**
     * Returns a collection of enum constants from the specified enum class that implements the MessageTypeEntry interface.
     *
     * @param enumClass the enum class implementing MessageTypeEntry
     * @param <E> the type of the enum
     * @return a collection of enum constants implementing MessageTypeEntry
     * @throws IllegalArgumentException if the provided class is not an enum
     */
    public static <E extends Enum<E> & MessageTypeEntry> Collection<E> fromImplementingEnum(Class<E> enumClass) {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("Provided class is not an enum: " + enumClass.getName());
        }

        return Arrays.asList(enumClass.getEnumConstants());
    }
}
