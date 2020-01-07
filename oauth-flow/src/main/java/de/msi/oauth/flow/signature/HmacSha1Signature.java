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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.msi.oauth.flow.common.CachedSupplier;
import de.msi.oauth.flow.params.ConsumerSecret;
import de.msi.oauth.flow.params.HttpMethod;
import de.msi.oauth.flow.params.Signature;
import de.msi.oauth.flow.params.SignatureMethodName;
import de.msi.oauth.flow.params.TokenSecret;
import de.msi.oauth.flow.signature.params.SignatureParams;
import de.msi.oauth.flow.signature.params.SignatureParamsHandler;
import de.msi.oauth.flow.signature.params.SignedTextSignatureParams;

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
     * The default implementation of the {@link HmacSha1Signature}.
     * 
     * @author Martin Siegemund
     */
    public static final class DefaultHmacSha1Signature extends DefaultSignedTextSignature implements HmacSha1Signature {
        private static final SignatureMethodName HMAC_SHA1 = SignatureMethodName.of("HMAC-SHA1");

        /**
         * Create a new instance.
         * 
         * @param endpoint the endpoint of the ongoing <code>HTTP</code> request
         * @param method the method of the ongoing <code>HTTP</code> request
         * @param consumerSecret the <code>consumer secret</code>
         */
        public DefaultHmacSha1Signature(URI endpoint, HttpMethod method, ConsumerSecret consumerSecret) {
            super(endpoint, method, consumerSecret);
        }

        @Override
        public SignatureMethodName oAuthName() {
            return HMAC_SHA1;
        }

        @Override
        public Signature signature(SignatureParams params) {
            return new CachedHmacSha1Signature(params);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).build();
        }

        private static final class SignedTextSignatureParamsExtraction
                implements SignatureParamsHandler<SignedTextSignatureParams, RuntimeException> {
            @Override
            public SignedTextSignatureParams visit(SignedTextSignatureParams params) throws RuntimeException {
                return params;
            }
        }

        /**
         * A lazy {@link Signature} implementation which uses the functionality of the {@link CachedSupplier}.
         *
         * @author Martin Siegemund
         */
        private final class CachedHmacSha1Signature extends CachedSupplier<String> implements Signature {
            private static final String CONCAT_FORMAT = "%s&%s";
            private static final String JAVA_ALGORITHM_NAME = "HmacSHA1";

            private final SignatureParams params;

            CachedHmacSha1Signature(SignatureParams params) {
                this.params = Objects.requireNonNull(params, "the SignatureParams are mandatory");
            }

            @Override
            protected String getAndCache() {
                try {
                    /* extract params */
                    SignedTextSignatureParams signatureParams = this.params
                            .handle(new SignedTextSignatureParamsExtraction());
                    /* create key */
                    SecretKeySpec signingKey = new SecretKeySpec(String
                            .format(CONCAT_FORMAT, consumerSecret().get(),
                                    signatureParams.tokenSecret().map(TokenSecret::get).orElse(StringUtils.EMPTY))
                            .getBytes(StandardCharsets.UTF_8), JAVA_ALGORITHM_NAME);
                    /* create and initialize algorithm */
                    Mac mac = Mac.getInstance(JAVA_ALGORITHM_NAME);
                    mac.init(signingKey);
                    /* generate hash */
                    byte[] hash = mac
                            .doFinal(signatureParams.signatureBaseString().get().getBytes(StandardCharsets.UTF_8));
                    /* return encoded */
                    return Base64.getEncoder().encodeToString(hash);
                } catch (Exception e) {
                    throw new IllegalStateException("could not calculate HMAC-SHA1 signature", e);
                }
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.params).build();
            }
        }
    }
}
