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

package io.github.msiegemund.oauth.flow.common;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Helps building {@link Lookup} instances for enumerations.
 * 
 * @author Martin Siegemund
 * @param <I> the type which implements the {@link Identifiable} interface
 * @param <E> the enumeration type
 */
public final class LookupBuilder<I extends Comparable<I>, E extends Enum<E> & Identifiable<I>> {

    private final Class<E> enumType;

    /**
     * Create a new builder instance.
     * 
     * @param enumType the enumeration type reference
     */
    public LookupBuilder(Class<E> enumType) {
        this.enumType = Objects.requireNonNull(enumType);
    }

    /**
     * Build the new {@link Lookup} instance.
     * 
     * @return the new {@link Lookup}
     */
    public Lookup<I, E> lookup() {
        return new DefaultLookup(Arrays.asList(this.enumType.getEnumConstants()).stream()
                .collect(Collectors.toUnmodifiableMap(this::map, Objects::requireNonNull)));
    }

    private I map(E enumConst) {
        return enumConst.identifier();
    }

    private final class DefaultLookup implements Lookup<I, E> {
        private final Map<I, E> lookup;

        DefaultLookup(Map<I, E> lookup) {
            this.lookup = lookup;
        }

        @Override
        public Optional<E> lookup(I key) {
            return Optional.ofNullable(this.lookup.get(key));
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.lookup).build();
        }
    }
}
