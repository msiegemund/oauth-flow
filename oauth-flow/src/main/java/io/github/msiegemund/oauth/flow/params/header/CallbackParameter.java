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

import java.net.URI;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Provides the capability of setting the <code>oauth_callback</code> in regard to the implementations behavior.
 *
 * @author Martin Siegemund
 */
interface CallbackParameter {
    /**
     * Set the <code>oauth_callback</code> information.
     * 
     * @param hpb the callback sink
     */
    void set(HeaderFieldsBuilderWithCallback hpb);

    /**
     * This implementation does not set any callback.
     *
     * @author Martin Siegemund
     */
    static final class NoCallback implements CallbackParameter {
        @Override
        public void set(@SuppressWarnings("unused") HeaderFieldsBuilderWithCallback hpb) {
            /* nothing to do */
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).build();
        }
    }

    /**
     * This implementation sets the given {@link URI} as callback.
     *
     * @author Martin Siegemund
     */
    static final class WithCallback implements CallbackParameter {
        private final URI callback;

        WithCallback(URI callback) {
            this.callback = Objects.requireNonNull(callback);
        }

        @Override
        public void set(HeaderFieldsBuilderWithCallback hpb) {
            hpb.callback(this.callback.toString());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("callback", this.callback)
                    .build();
        }
    }

    /**
     * This implementation sets the callback to <code>oob</code>.
     *
     * @author Martin Siegemund
     */
    static final class OutOfBandCallback implements CallbackParameter {
        private static final String CALLBACK_OUT_OF_BAND = "oob";

        @Override
        public void set(HeaderFieldsBuilderWithCallback hpb) {
            hpb.callback(CALLBACK_OUT_OF_BAND);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).build();
        }
    }
}
