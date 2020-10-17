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

package io.github.msiegemund.oauth.flow.params.header;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.msiegemund.oauth.flow.params.header.fields.HeaderField;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderFields;

/**
 * Represents an implementation which provides the header fields, ordered by
 * the natural ordering of the header fields keys.
 *
 * @author Martin Siegemund
 */
final class OrderedHeaderFields implements HeaderFields {
    private final Set<OrderedHeaderField> fields = new TreeSet<>();

    void add(String key, String value) {
        if (!this.fields.add(new OrderedHeaderField(key, value))) {
            throw new IllegalArgumentException(String.format("key-value pair '%s=%s' already present", key, value));
        }
    }

    void add(HeaderField headerField) {
        add(headerField.key(), headerField.value().orElse(null));
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