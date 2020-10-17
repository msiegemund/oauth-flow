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

package io.github.msiegemund.oauth.flow.signature.params;

/**
 * Provides the capability of handling {@link SignatureParams} instances in a
 * typesafe way.
 * 
 * @author Martin Siegemund
 * @param <R> the type of the return value
 * @param <E> the type of the exception
 */
public interface SignatureParamsHandler<R, E extends Exception> {

    /**
     * Handle a {@link PlainTextSignatureParams} instance.
     * 
     * @param params the {@link PlainTextSignatureParams} instance
     * @return the result
     * @throws E if the handling failed
     */
    default R visit(@SuppressWarnings("unused") PlainTextSignatureParams params) throws E {
        throw new UnsupportedOperationException(Commons.NOT_IMPLEMENTED);
    }

    /**
     * Handle a {@link SignedTextSignatureParams} instance.
     * 
     * @param params the {@link SignedTextSignatureParams} instance
     * @return the result
     * @throws E if the handling failed
     */
    default R visit(@SuppressWarnings("unused") SignedTextSignatureParams params) throws E {
        throw new UnsupportedOperationException(Commons.NOT_IMPLEMENTED);
    }
}
