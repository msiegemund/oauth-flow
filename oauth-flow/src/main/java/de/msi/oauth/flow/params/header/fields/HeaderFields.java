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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Represents a bunch of header fields.
 * <p>
 * Use {@link #of(Set)} or {@link #empty()} to create an instance of a basic implementation of
 * {@link HeaderFields}.
 *
 * @author Martin Siegemund
 */
public interface HeaderFields {
    /**
     * Retrieve the header fields, represented as a {@link Map} whereas the
     * <code>key</code> of every entry represents a header field's key and the
     * <code>value</code> of every entry represents its associated header
     * field's value.
     * 
     * @return the header fields
     */
    Collection<HeaderField> fields();

    /**
     * Creates a new instance of {@link HeaderFields}.
     * 
     * @param headerFields a {@link Set} of {@link HeaderField} instances which
     *        represent the created instance
     * @return a new instance
     */
    static HeaderFields of(Set<? extends HeaderField> headerFields) {
        return new DefaultHeaderFields(headerFields);
    }

    /**
     * Creates a new instance of {@link HeaderFields} which does not contain any fields.
     * 
     * @return a new and empty instance
     */
    static HeaderFields empty() {
        return new DefaultHeaderFields(Collections.emptySet());
    }
}
