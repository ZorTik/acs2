package me.zort.acs.http.internal.adapters;

import com.google.gson.JsonDeserializer;

/**
 * A deserializer interface for ACS HTTP request body object.
 *
 * @param <T> the type of the object to deserialize
 */
public interface AcsHttpDeserializer<T> extends JsonDeserializer<T> {

    /**
     * Returns the type of the object that this deserializer handles.
     *
     * @return the class type of the object
     */
    Class<T> getType();
}
