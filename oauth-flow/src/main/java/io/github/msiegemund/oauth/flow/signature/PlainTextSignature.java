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

import io.github.msiegemund.oauth.flow.params.ConsumerSecret;

/**
 * This represents to the signature type <code>PLAINTEXT</code>.
 * <p>
 * It is recommended to use the {@link DefaultPlainTextSignature}
 * implementation.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor21">PLAINTEXT</a>
 */
public interface PlainTextSignature extends SignatureMethod {

    @Override
    default <R, E extends Exception> R handle(SignatureMethodHandler<R, E> handler) throws E {
        return handler.visit(this);
    }

    /**
     * Create a new instance.
     * 
     * @param consumerSecret the mandatory <code>consumer secret</code>
     * @return the new signature method
     */
    static PlainTextSignature of(ConsumerSecret consumerSecret) {
        return new DefaultPlainTextSignature(consumerSecret);
    }
}
