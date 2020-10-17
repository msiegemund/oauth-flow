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

package io.github.msiegemund.oauth.flow.params.header;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.msiegemund.oauth.flow.params.ConsumerKey;
import io.github.msiegemund.oauth.flow.params.Token;
import io.github.msiegemund.oauth.flow.params.TokenSecret;
import io.github.msiegemund.oauth.flow.params.VerificationCode;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderFields;
import io.github.msiegemund.oauth.flow.signature.HmacSha1Signature;
import io.github.msiegemund.oauth.flow.signature.PlainTextSignature;
import io.github.msiegemund.oauth.flow.signature.SignatureMethod;
import io.github.msiegemund.oauth.flow.signature.SignatureMethodHandler;
import io.github.msiegemund.oauth.flow.signature.base.DefaultSignatureBaseStringBuilder;
import io.github.msiegemund.oauth.flow.signature.base.SignatureBaseStringBuilder;
import io.github.msiegemund.oauth.flow.signature.params.PlainTextSignatureParams;
import io.github.msiegemund.oauth.flow.signature.params.SignatureParams;
import io.github.msiegemund.oauth.flow.signature.params.SignedTextSignatureParams;
import io.github.msiegemund.oauth.flow.signature.params.PlainTextSignatureParams.DefaultPlainTextSignatureParams;
import io.github.msiegemund.oauth.flow.signature.params.SignedTextSignatureParams.DefaultSignedTextSignatureParams;

/**
 * The default implementation of the {@link HeaderFieldsBuilder}}.
 *
 * @author Martin Siegemund
 */
final class DefaultHeaderFieldsBuilder implements HeaderFieldsBuilder, HeaderFieldsBuilderWithCallback {

    private static final String OAUTH_TOKEN_KEY = "oauth_token";
    private static final String CONSUMER_KEY_KEY = "oauth_consumer_key";
    private static final String SIGNATURE_METHOD_KEY = "oauth_signature_method";
    private static final String VERSION_KEY = "oauth_version";
    private static final String OAUTH_VERSION = "1.0";
    private static final String CALLBACK_KEY = "oauth_callback";
    private static final String TIMESTAMP_KEY = "oauth_timestamp";
    private static final String NONCE_KEY = "oauth_nonce";
    private static final String SIGNATURE_KEY = "oauth_signature";
    private static final String VERIFIER_KEY = "oauth_verifier";

    private final OrderedHeaderFields fields = new OrderedHeaderFields();
    private final AtomicReference<TokenSecret> ts = new AtomicReference<>();
    private final OauthHeaderFieldsValues ohpv;
    private final CallbackParameter cp;

    /**
     * Create a new instance.
     * 
     * @param ohfv mandatory values for creating the field information
     * @param cp the callback behavior
     */
    DefaultHeaderFieldsBuilder(OauthHeaderFieldsValues ohfv, CallbackParameter cp) {
        this.ohpv = Objects.requireNonNull(ohfv);
        this.cp = Objects.requireNonNull(cp);
    }

    @Override
    public HeaderFieldsBuilder consumerKey(ConsumerKey consumerKey) {
        this.fields.add(CONSUMER_KEY_KEY, consumerKey.get());
        return this;
    }

    @Override
    public HeaderFieldsBuilder versionInformation() {
        this.fields.add(VERSION_KEY, OAUTH_VERSION);
        return this;
    }

    @Override
    public HeaderFieldsBuilder callback(String callback) {
        this.fields.add(CALLBACK_KEY, callback);
        return this;
    }

    @Override
    public HeaderFieldsBuilder token(Token token) {
        this.fields.add(OAUTH_TOKEN_KEY, token.get());
        return this;
    }

    @Override
    public HeaderFieldsBuilder tokenSecret(TokenSecret tokenSecret) {
        this.ts.set(tokenSecret);
        return this;
    }

    @Override
    public HeaderFieldsBuilder verifier(VerificationCode verifier) {
        this.fields.add(VERIFIER_KEY, verifier.get());
        return this;
    }

    @Override
    public HeaderFieldsBuilder additionalParameter(HeaderFields additionalParams) {
        additionalParams.fields().forEach(this.fields::add);
        return this;
    }

    @Override
    public HeaderFields build(SignatureMethod signatureMethod) {
        /* handle the callback field */
        this.cp.set(this);
        /* add timestamp */
        this.fields.add(TIMESTAMP_KEY, this.ohpv.timestamp());
        /* add nonce */
        this.fields.add(NONCE_KEY, this.ohpv.nonce());
        /* add signature */
        this.fields.add(SIGNATURE_METHOD_KEY, signatureMethod.oAuthName().get());
        this.fields.add(SIGNATURE_KEY,
                URLEncoder.encode(
                        (signatureMethod.signature(signatureMethod.handle(new SignatureParamBuilder()))).get(),
                        StandardCharsets.UTF_8));
        /* return complete header */
        return this.fields;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.ohpv).append(this.cp).build();
    }

    private final class SignatureParamBuilder
            implements SignatureMethodHandler<SignatureParams, UnsupportedOperationException> {
        /**
         * Returns an instance of {@link DefaultSignedTextSignatureParams} with
         * using {@link DefaultHeaderFieldsBuilder#fields} as content.
         */
        @Override
        public SignatureParams visit(HmacSha1Signature method) throws UnsupportedOperationException {
            /* make use of base string builder */
            SignatureBaseStringBuilder sbsb = new DefaultSignatureBaseStringBuilder(method.method(), method.endpoint());
            /* put all header parameters into signature builder */
            DefaultHeaderFieldsBuilder.this.fields.fields().forEach(sbsb::add);
            /* return params containing the base string */
            return new SignedTextSignatureParams.DefaultSignedTextSignatureParams(sbsb.build(),
                    DefaultHeaderFieldsBuilder.this.ts.get());
        }

        /**
         * Returns an instance of {@link DefaultPlainTextSignatureParams}
         * without specifying the token secret, due to the early process flow.
         */
        @Override
        public SignatureParams visit(@SuppressWarnings("unused") PlainTextSignature method)
                throws UnsupportedOperationException {
            return new PlainTextSignatureParams.DefaultPlainTextSignatureParams(
                    DefaultHeaderFieldsBuilder.this.ts.get());
        }
    }
}
