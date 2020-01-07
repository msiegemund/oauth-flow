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

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The default implementation of {@link HeaderField}.
 *
 * @author Martin Siegemund
 */
final class DefaultHeaderField implements HeaderField {
    private final String key;
    private final String value;

    DefaultHeaderField(String key, String value) {
        this.key = Objects.requireNonNull(key, "the key is mandatorys");
        this.value = value;
    }

    DefaultHeaderField(String key) {
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.key).append(this.value).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultHeaderField)) {
            return false;
        }
        DefaultHeaderField other = (DefaultHeaderField) obj;
        return new EqualsBuilder().append(this.key, other.key).append(this.value, other.value).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("key", this.key)
                .append("value", this.value).build();
    }
}
