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

package de.msi.oauth.flow.params;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a <code>request token</code>.
 * <p>
 * use {@link #of(String)} to create a new instance.
 *
 * @author Martin Siegemund
 */
@FunctionalInterface
public interface RequestToken extends Token {
    /**
     * Create a new {@link RequestToken}.
     * 
     * @param value the raw value
     * @return a new {@link RequestToken}
     */
    static RequestToken of(String value) {
        return new DefaultRequestToken(value);
    }

    /**
     * The default implementation of {@link RequestToken}.
     *
     * @author Martin Siegemund
     */
    static final class DefaultRequestToken extends StringParam implements RequestToken {
        DefaultRequestToken(String value) {
            super(value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).build();
        }
    }
}
