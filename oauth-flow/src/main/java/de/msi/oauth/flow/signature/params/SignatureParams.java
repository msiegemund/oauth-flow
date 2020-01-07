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

package de.msi.oauth.flow.signature.params;

import java.util.Optional;

import de.msi.oauth.flow.params.TokenSecret;

/**
 * A generic signature parameter type, required to create the <code>OAuth</code>
 * signature.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#signing_process">Signing
 *      Requests</a>
 */
public interface SignatureParams {
    /**
     * Retrieve the token secret.
     * 
     * @return the secret or {@link Optional#empty()} if not preset
     */
    Optional<TokenSecret> tokenSecret();

    /**
     * Handle the {@link SignatureParams} in a typesafe way.
     * 
     * @param <R> the type of the return value
     * @param <E> the type of the thrown exception
     * @param handler the {@link SignatureParamsHandler}
     * @return the result
     * @throws E if the handling failed
     */
    <R, E extends Exception> R handle(SignatureParamsHandler<R, E> handler) throws E;
}
