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
 * Represents an <code>access token</code>.
 * <p>
 * Use {@link #of(String)} to create a new instance.
 *
 * @author Martin Siegemund
 */
@FunctionalInterface
public interface AccessToken extends Token {
    /**
     * Create a new instance.
     * 
     * @param value the raw value
     * @return a new {@link AccessToken}
     */
    static AccessToken of(String value) {
        return new DefaultAccessToken(value);
    }

    /**
     * The default implementation of {@link AccessToken}.
     *
     * @author Martin Siegemund
     */
    static final class DefaultAccessToken extends StringParam implements AccessToken {
        DefaultAccessToken(String value) {
            super(value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).build();
        }
    }
}
