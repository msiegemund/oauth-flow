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

package io.github.msiegemund.oauth.flow.params;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a <code>oauth_signature</code>.
 * <p>
 * Use {@link #of(String)} to create a new instance.
 *
 * @author Martin Siegemund
 */
@FunctionalInterface
public interface Signature extends OAuthParam {
    /**
     * Create a new instance.
     * 
     * @param value the raw value
     * @return the new {@link Signature}
     */
    static Signature of(String value) {
        return new DefaultSignature(value);
    }

    /**
     * The default implementation of {@link Signature}.
     *
     * @author Martin Siegemund
     */
    static final class DefaultSignature extends StringParam implements Signature {
        DefaultSignature(String value) {
            super(value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).build();
        }
    }
}
