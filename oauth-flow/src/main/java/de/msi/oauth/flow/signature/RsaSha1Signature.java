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

package de.msi.oauth.flow.signature;

import java.net.URI;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.msi.oauth.flow.params.ConsumerSecret;
import de.msi.oauth.flow.params.HttpMethod;
import de.msi.oauth.flow.params.Signature;
import de.msi.oauth.flow.params.SignatureMethodName;
import de.msi.oauth.flow.signature.params.SignatureParams;

/**
 * Represents the <code>RSA-SHA1</code> signature method.
 * <p>
 * It is recommended to use the {@link DefaultRsaSha1Signature} implementation.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor18">RSA-SHA1</a>
 */
public interface RsaSha1Signature extends SignedTextSignature {
    @Override
    default <R, E extends Exception> R handle(SignatureMethodHandler<R, E> handler) throws E {
        return handler.visit(this);
    }

    /**
     * The default implementation of the {@link RsaSha1Signature}.
     * <p>
     * <b>Attention: not yet implemented</b>
     *
     * @author Martin Siegemund
     */
    public static final class DefaultRsaSha1Signature extends DefaultSignedTextSignature implements RsaSha1Signature {
        private static final SignatureMethodName RSA_SHA1 = SignatureMethodName.of("RSA-SHA1");

        /**
         * Create a new instance.
         * 
         * @param endpoint the endpoint of the ongoing <code>HTTP</code> request
         * @param method the method of the ongoing <code>HTTP</code> request
         * @param consumerSecret the <code>consumer secret</code>
         */
        public DefaultRsaSha1Signature(URI endpoint, HttpMethod method, ConsumerSecret consumerSecret) {
            super(endpoint, method, consumerSecret);
        }

        @Override
        public SignatureMethodName oAuthName() {
            return RSA_SHA1;
        }

        @Override
        public Signature signature(@SuppressWarnings("unused") SignatureParams params) {
            // TODO implement
            throw new UnsupportedOperationException("RSA-SHA1 is not yet implemented");
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).build();
        }
    }
}
