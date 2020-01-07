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

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.msi.oauth.flow.params.SignatureBaseString;
import de.msi.oauth.flow.params.TokenSecret;

/**
 * Parameter implementation required for the <code>HMAC-SHA1</code> method and
 * for the <code>RSA-SHA1</code> method.
 * <p>
 * It is recommended to use the {@link DefaultSignedTextSignatureParams}
 * implementation.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor15">HMAC-SHA1</a>
 * @see <a href="https://oauth.net/core/1.0a/#anchor18">RSA-SHA1</a>
 */
public interface SignedTextSignatureParams extends SignatureParams {
    /**
     * Retrieve the <code>signature base string</code>.
     * 
     * @return the <code>signature base string</code>
     * @see <a href="https://oauth.net/core/1.0a/#anchor13">Signature Base
     *      String</a>
     */
    SignatureBaseString signatureBaseString();

    @Override
    default <R, E extends Exception> R handle(SignatureParamsHandler<R, E> handler) throws E {
        return handler.visit(this);
    }

    /**
     * The default implementation of the {@link SignedTextSignatureParams}.
     * 
     * @author Martin Siegemund
     */
    public static final class DefaultSignedTextSignatureParams implements SignedTextSignatureParams {
        private final SignatureBaseString signatureBaseString;
        private final TokenSecret tokenSecret;

        /**
         * Create a new instance.
         * 
         * @param signatureBaseString the <code>signature base string</code>
         * @param tokenSecret the <code>token secret</code> (can be
         *        <code>null</code>)
         */
        public DefaultSignedTextSignatureParams(SignatureBaseString signatureBaseString, TokenSecret tokenSecret) {
            this.signatureBaseString = Objects.requireNonNull(signatureBaseString,
                    "the signature base string is mandatory");
            this.tokenSecret = tokenSecret;
        }

        /**
         * Create a new instance without specifying a <code>token secret</code>.
         * 
         * @param signatureBaseString the <code>signature base string</code>
         */
        public DefaultSignedTextSignatureParams(SignatureBaseString signatureBaseString) {
            this(signatureBaseString, null);
        }

        @Override
        public SignatureBaseString signatureBaseString() {
            return this.signatureBaseString;
        }

        @Override
        public Optional<TokenSecret> tokenSecret() {
            return Optional.ofNullable(this.tokenSecret);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.signatureBaseString).build();
        }
    }
}
