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

/**
 * Provides the capability to handle {@link SignatureMethod} instances in a
 * typesafe way.
 *
 * @author Martin Siegemund
 * @param <R> the type of the return value
 * @param <E> the type of the declared exception
 */
public interface SignatureMethodHandler<R, E extends Exception> {

    /**
     * Handle {@link PlainTextSignature} in a typesafe way.
     * 
     * @param method the {@link PlainTextSignature}
     * @return the return value
     * @throws E if any failure occurred
     */
    default R visit(@SuppressWarnings("unused") PlainTextSignature method) throws E {
        throw new UnsupportedOperationException(Commons.NOT_IMPLEMENTED);
    }

    /**
     * Handle {@link HmacSha1Signature} in a typesafe way.
     * 
     * @param method the {@link HmacSha1Signature}
     * @return the return value
     * @throws E if any failure occurred
     */
    default R visit(@SuppressWarnings("unused") HmacSha1Signature method) throws E {
        throw new UnsupportedOperationException(Commons.NOT_IMPLEMENTED);
    }

    /**
     * Handle {@link RsaSha1Signature} in a typesafe way.
     * 
     * @param method the {@link RsaSha1Signature}
     * @return the return value
     * @throws E if any failure occurred
     */
    default R visit(@SuppressWarnings("unused") RsaSha1Signature method) throws E {
        throw new UnsupportedOperationException(Commons.NOT_IMPLEMENTED);
    }
}
