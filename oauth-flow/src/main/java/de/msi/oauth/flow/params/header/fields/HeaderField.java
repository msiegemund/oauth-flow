/**
 *  Copyright 2020 Martin Siegemund
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.msi.oauth.flow.params.header.fields;

import java.util.Optional;

/**
 * Represents a single header field, consisting of a <code>Key</code> and a
 * <code>value</code>.
 * <p>
 * Use {@link #of(String)} or {@link #of(String, String)} to create an instance
 * of a basic
 * {@link HeaderField} which implements {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 *
 * @author Martin Siegemund
 */
public interface HeaderField {
    /**
     * Retrieve the header fields key.
     * 
     * @return the key
     */
    String key();

    /**
     * Retrieve the header fields value. The value may be empty.
     * 
     * @return the value or {@link Optional#empty()}
     */
    Optional<String> value();

    /**
     * Creates a new instance of a {@link HeaderField}.
     * 
     * @param key the fields key
     * @param value the fields value (may be <code>null</code>)
     * @return the new instance
     */
    static HeaderField of(String key, String value) {
        return new DefaultHeaderField(key, value);
    }

    /**
     * Creates a new instance of a {@link HeaderField} with an empty
     * (<code>null</code>) value.
     * 
     * @param key the fields key
     * @return the new instance
     */
    static HeaderField of(String key) {
        return new DefaultHeaderField(key);
    }
}
