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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.msiegemund.oauth.flow.params.ConsumerSecret;
import io.github.msiegemund.oauth.flow.params.Signature;
import io.github.msiegemund.oauth.flow.params.SignatureMethodName;
import io.github.msiegemund.oauth.flow.params.TokenSecret;
import io.github.msiegemund.oauth.flow.signature.params.PlainTextSignatureParams;
import io.github.msiegemund.oauth.flow.signature.params.SignatureParams;
import io.github.msiegemund.oauth.flow.signature.params.SignatureParamsHandler;

/**
 * The default implementation of the {@link PlainTextSignature}.
 *
 * @author Martin Siegemund
 */
final class DefaultPlainTextSignature implements PlainTextSignature {

    private static final String CONCAT_FORMAT = "%s&%s";
    private static final SignatureMethodName PLAINTEXT = SignatureMethodName.of("PLAINTEXT");
    private final ConsumerSecret consumerSecret;

    /**
     * Create a new instance.
     * 
     * @param consumerSecret the mandatory <code>consumer secret</code>
     */
    DefaultPlainTextSignature(ConsumerSecret consumerSecret) {
        this.consumerSecret = Objects.requireNonNull(consumerSecret, "the consumer secret is mandatory");
    }

    @Override
    public SignatureMethodName oAuthName() {
        return PLAINTEXT;
    }

    @Override
    public ConsumerSecret consumerSecret() {
        return this.consumerSecret;
    }

    @Override
    public Signature signature(SignatureParams params) {
        return Signature.of(String.format(CONCAT_FORMAT, encode(this.consumerSecret.get()), encode(params
                .handle(new PlainTextSignatureParamExtraction()).tokenSecret().map(TokenSecret::get).orElse(null))));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).build();
    }

    private static String encode(String toEncode) {
        return toEncode == null ? StringUtils.EMPTY : URLEncoder.encode(toEncode, StandardCharsets.UTF_8);
    }

    private static final class PlainTextSignatureParamExtraction
            implements SignatureParamsHandler<PlainTextSignatureParams, RuntimeException> {
        @Override
        public PlainTextSignatureParams visit(PlainTextSignatureParams params) throws RuntimeException {
            return params;
        }
    }
}
