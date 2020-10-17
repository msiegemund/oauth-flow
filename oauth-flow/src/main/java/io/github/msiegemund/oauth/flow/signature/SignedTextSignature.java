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

package io.github.msiegemund.oauth.flow.signature;

import java.net.URI;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.msiegemund.oauth.flow.params.ConsumerSecret;
import io.github.msiegemund.oauth.flow.params.HttpMethod;

/**
 * A hidden intermediate interface to bundle functionalities which represent all
 * signed text signatures.
 * 
 * @author Martin Siegemund
 */
interface SignedTextSignature extends SignatureMethod {
    /**
     * Retrieve the endpoint which gets addressed by the current
     * <code>HTTP</code> request.
     * 
     * @return the endpoint
     */
    URI endpoint();

    /**
     * Retrieve the {@link HttpMethod} which gets used by the current request.
     * 
     * @return the method
     */
    HttpMethod method();

    /**
     * Bundles the common functionalities.
     *
     * @author Martin Siegemund
     */
    static abstract class DefaultSignedTextSignature implements SignedTextSignature {

        private final URI endpoint;
        private final HttpMethod method;
        private final ConsumerSecret consumerSecret;

        DefaultSignedTextSignature(URI endpoint, HttpMethod method, ConsumerSecret consumerSecret) {
            this.endpoint = Objects.requireNonNull(endpoint, "the endpoint is mandatory");
            this.method = Objects.requireNonNull(method, "the http method is mandatory");
            this.consumerSecret = Objects.requireNonNull(consumerSecret, "the consumer secret is mandatory");
        }

        @Override
        public final URI endpoint() {
            return this.endpoint;
        }

        @Override
        public final HttpMethod method() {
            return this.method;
        }

        @Override
        public final ConsumerSecret consumerSecret() {
            return this.consumerSecret;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("endpoint", this.endpoint)
                    .append("method", this.method).build();
        }
    }
}
