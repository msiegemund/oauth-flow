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

package de.msi.oauth.flow.signature.base;

import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

final class Parameter implements Comparable<Parameter> {
    private final String key;
    private final String value;

    Parameter(String key, String value) {
        this.key = Objects.requireNonNull(key);
        this.value = value;
    }

    String normalized() {
        return String.format("%s=%s", this.key, this.value);
    }

    @Override
    public int compareTo(Parameter o) {
        CompareToBuilder ctb = new CompareToBuilder().append(this.key, o.key);
        int comparison = ctb.toComparison();
        if (comparison == 0) {
            comparison = ctb.append(this.value, o.value).toComparison();
        }
        return comparison;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.key).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Parameter other = (Parameter) obj;
        return new EqualsBuilder().append(this.key, other.key).isEquals();
    }
}