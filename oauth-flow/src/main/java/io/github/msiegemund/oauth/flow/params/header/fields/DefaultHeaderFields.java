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

package io.github.msiegemund.oauth.flow.params.header.fields;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The default implementation of {@link HeaderFields}.
 *
 * @author Martin Siegemund
 */
final class DefaultHeaderFields implements HeaderFields {
    private final Set<HeaderField> fields = new HashSet<>();

    /**
     * Create a new instance.
     * 
     * @param headerFields the raw header fields
     */
    DefaultHeaderFields(Set<? extends HeaderField> headerFields) {
        Optional.ofNullable(headerFields).ifPresent(this.fields::addAll);
    }

    @Override
    public Collection<HeaderField> fields() {
        return Collections.unmodifiableCollection(this.fields);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("fields", this.fields).build();
    }
}
