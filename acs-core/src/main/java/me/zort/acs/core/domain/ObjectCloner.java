package me.zort.acs.core.domain;

/**
 * Interface for cloning objects of a specific type.
 *
 * @param <T> the type of object to be cloned
 */
public interface ObjectCloner<T> {

    /**
     * Creates and returns a deep copy of the given object.
     *
     * @param object the object to clone
     * @return a deep copy of the provided object
     */
    T cloneObject(T object);
}
