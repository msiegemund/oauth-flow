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
import io.github.msiegemund.oauth.flow.params.Signature;
import io.github.msiegemund.oauth.flow.params.SignatureMethodName;
import io.github.msiegemund.oauth.flow.signature.params.SignatureParams;

/**
 * This functionality refers to the different ways of sign requests within the
 * <code>OAuth authentication</code> process.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#signing_process">Signing
 *      Requests</a>
 */
public interface SignatureMethod {
    /**
     * Retrieve the name of the signature method.
     * 
     * @return the name
     */
    SignatureMethodName oAuthName();

    /**
     * Retrieve the <code>consumer secret</code> which is bound to a specific
     * service provider
     * 
     * @return the <code>consumer secret</code>
     */
    ConsumerSecret consumerSecret();

    /**
     * Generate the signature.
     * 
     * @param params the {@link SignatureParams} which are required by
     *        this {@link SignatureMethod} to generate the signature
     * @return the signature
     */
    Signature signature(SignatureParams params);

    /**
     * Handle this {@link SignatureMethod} in a typesafe way by providing a
     * {@link SignatureMethodHandler}.
     * 
     * @param <R> the desired return values type
     * @param <E> the type of exception which is allowed to be thrown
     * @param handler the {@link SignatureMethodHandler}
     * @return the result of the handling process
     * @throws E if a failure occurred while handling the
     *         {@link SignatureMethod}
     */
    <R, E extends Exception> R handle(SignatureMethodHandler<R, E> handler) throws E;
}
