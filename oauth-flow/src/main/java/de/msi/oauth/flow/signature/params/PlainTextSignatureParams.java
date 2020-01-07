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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.msi.oauth.flow.params.TokenSecret;

/**
 * Parameter implementation required for the <code>PLAINTEXT</code> method.
 * <p>
 * It is recommended to use the {@link DefaultPlainTextSignatureParams}
 * implementation.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor21">PLAINTEXT</a>
 */
public interface PlainTextSignatureParams extends SignatureParams {

    @Override
    default <R, E extends Exception> R handle(SignatureParamsHandler<R, E> handler) throws E {
        return handler.visit(this);
    }

    /**
     * The default implementation of the {@link PlainTextSignatureParams}.
     *
     * @author Martin Siegemund
     */
    public static final class DefaultPlainTextSignatureParams implements PlainTextSignatureParams {
        private final TokenSecret tokenSecret;

        /**
         * Create a new instance.
         * 
         * @param tokenSecret the token secret (can be <code>null</code>)
         */
        public DefaultPlainTextSignatureParams(TokenSecret tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        /**
         * Create a new instance without specifying the
         * <code>token secret</code>.
         */
        public DefaultPlainTextSignatureParams() {
            this(null);
        }

        @Override
        public Optional<TokenSecret> tokenSecret() {
            return Optional.ofNullable(this.tokenSecret);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).build();
        }
    }
}
