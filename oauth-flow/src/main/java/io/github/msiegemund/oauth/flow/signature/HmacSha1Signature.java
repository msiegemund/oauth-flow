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

import io.github.msiegemund.oauth.flow.params.ConsumerSecret;
import io.github.msiegemund.oauth.flow.params.HttpMethod;

/**
 * Represents the <code>HMAC-SHA1</code> signature method.
 * <p>
 * It is recommended to use the {@link DefaultHmacSha1Signature} implementation.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor15">HMAC-SHA1</a>
 */
public interface HmacSha1Signature extends SignedTextSignature {
    @Override
    default <R, E extends Exception> R handle(SignatureMethodHandler<R, E> handler) throws E {
        return handler.visit(this);
    }

    /**
     * Create a new instance.
     * 
     * @param endpoint the endpoint of the ongoing <code>HTTP</code> request
     * @param method the method of the ongoing <code>HTTP</code> request
     * @param consumerSecret the <code>consumer secret</code>
     * @return the new signature instance
     */
    static HmacSha1Signature of(URI endpoint, HttpMethod method, ConsumerSecret consumerSecret) {
        return new DefaultHmacSha1Signature(endpoint, method, consumerSecret);
    }
}
