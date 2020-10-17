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

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.msiegemund.oauth.flow.params.header.fields.HeaderField;

/**
 * Represents a {@link HeaderField} which implements the {@link Comparable} interface to provide the capability of being
 * ordered ad stated in chapter <code>9.1.1</code> of the <code>OAuth</code> specification.
 *
 * @author Martin Siegemund
 * @see #compareTo(HeaderField)
 */
final class OrderedHeaderField implements HeaderField, Comparable<HeaderField> {
    private final String key;
    private final String value;

    /**
     * Create a new instance.
     * 
     * @param key the key of this field
     * @param value the value of this field (can be <code>null</code>)
     */
    OrderedHeaderField(String key, String value) {
        this.key = Objects.requireNonNull(key, "the key is mandatorys");
        this.value = value;
    }

    /**
     * Create a new instance with an empty value (<code>null</code>).
     * 
     * @param key the key of this field.
     */
    OrderedHeaderField(String key) {
        this(key, null);
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public Optional<String> value() {
        return Optional.ofNullable(this.value);
    }

    /**
     * Represents the comparison as defined in
     * <code>9.1.1. Normalize Request Parameters</code> of
     * <code>OAuth Core 1.0 Revision A</code>.
     * 
     * @see <a href="https://oauth.net/core/1.0a/#anchor13">Signature Base
     *      String</a>
     */
    @Override
    public int compareTo(HeaderField o) {
        CompareToBuilder ctb = new CompareToBuilder().append(this.key, o.key());
        int result = ctb.toComparison();
        return result != 0 ? result : ctb.append(this.value, o.value().orElse(null)).toComparison();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.key).append(this.value).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrderedHeaderField)) {
            return false;
        }
        OrderedHeaderField other = (OrderedHeaderField) obj;
        return new EqualsBuilder().append(this.key, other.key).append(this.value, other.value).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("key", this.key)
                .append("value", this.value).build();
    }
}
