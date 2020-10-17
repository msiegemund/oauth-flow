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

package io.github.msiegemund.oauth.flow;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.msiegemund.oauth.flow.params.AccessToken;
import io.github.msiegemund.oauth.flow.params.AccessTokenSecret;
import io.github.msiegemund.oauth.flow.params.ConsumerKey;
import io.github.msiegemund.oauth.flow.params.ConsumerSecret;
import io.github.msiegemund.oauth.flow.params.HttpMethod;
import io.github.msiegemund.oauth.flow.params.RequestToken;
import io.github.msiegemund.oauth.flow.params.RequestTokenSecret;
import io.github.msiegemund.oauth.flow.params.VerificationCode;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderField;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderFields;
import io.github.msiegemund.oauth.flow.signature.HmacSha1Signature;
import io.github.msiegemund.oauth.flow.signature.PlainTextSignature;
import io.github.msiegemund.oauth.flow.signature.SignatureMethod;
import io.github.msiegemund.oauth.flow.user.UserAccessTokenParameter;
import io.github.msiegemund.oauth.flow.user.UserAuthorization;
import io.github.msiegemund.oauth.flow.user.UserObtainAccessToken;
import io.github.msiegemund.oauth.flow.user.UserObtainRequestToken;
import io.github.msiegemund.oauth.flow.user.UserRequestToken;

final class ComplexFlowTest {
    private static final Log LOG = LogFactory.getLog(ComplexFlowTest.class);
    /* the OAUTH header keys */
    private static final String OAUTH_VERIFIER_KEY = "oauth_verifier";
    private static final String OAUTH_CALLBACK_KEY = "oauth_callback";
    private static final String OAUTH_NONCE_KEY = "oauth_nonce";
    private static final String OAUTH_TIMESTAMP_KEY = "oauth_timestamp";
    private static final String OAUTH_SIGNATURE_KEY = "oauth_signature";
    private static final String OAUTH_VERSION_KEY = "oauth_version";
    private static final String OAUTH_SIGNATURE_METHOD_KEY = "oauth_signature_method";
    private static final String OAUTH_TOKEN_KEY = "oauth_token";
    private static final String OAUTH_CONSUMER_KEY_KEY = "oauth_consumer_key";
    private static final String OAUTH_VERSION_NUMBER = "1.0";
    /* shared variables */
    private static final String PROT_RES_HV_2_VAL = "original";
    private static final String PROT_RES_HV_2_KEY = "size";
    private static final String PROT_RES_HV_1_VAL = "vacation.jpg";
    private static final String PROT_RES_HV_1_KEY = "file";
    private static final String SPP_1_VAL = "spp-1-val";
    private static final String SERVICE_PROVIDER_PARAM_1 = "service-provider-param-1";
    private static final URI CALLBACK = URI.create("http://printer.example.com/request_token_ready");
    private static final ConsumerKey CONSUMER_KEY = ConsumerKey.of("dpf43f3p2l4k3l03");
    private static final ConsumerSecret CONSUMER_SECRET = ConsumerSecret.of("kd94hf93k423kf44");
    private static final HmacSha1Signature HMAC_SHA1_SIGNATURE_FOR_ACCESS_TOKEN = HmacSha1Signature
            .of(URI.create("https://photos.example.net/access_token"), HttpMethod.POST, CONSUMER_SECRET);
    private static final HmacSha1Signature HMAC_SHA1_SIGNATURE_FOR_PROTECTED_RESOURCE_ACCESS = HmacSha1Signature
            .of(URI.create("http://photos.example.net/photos"), HttpMethod.GET, CONSUMER_SECRET);
    private static final String EXPECTED_PLAIN_TEXT_SIGNATURE = "kd94hf93k423kf44%26";
    private static final SignatureMethod PLAIN_TEXT_SIGNATURE = PlainTextSignature.of(CONSUMER_SECRET);
    private static final RequestTokenSecret REQUEST_TOKEN_SECRET = RequestTokenSecret.of("hdhd0244k9j7ao03");
    private static final RequestToken REQUEST_TOKEN = RequestToken.of("hh5s93j4hdidpola");
    private static final VerificationCode VERIFICATION_CODE = VerificationCode.of("hfdp7dh39dks9884");
    private static final AccessTokenSecret ACCESS_TOKEN_SECRET = AccessTokenSecret.of("pfkkdhi9sl3r4s00");
    private static final AccessToken ACCESS_TOKEN = AccessToken.of("nnch734d00sl2jdk");

    @Test
    void test() {
        /* create main flow entry point */
        OAuthFlow oauthFlow = OAuthFlow.of(new RequestTokenFlowParam.RequestTokenFlowParamBuilder(CONSUMER_KEY,
                PLAIN_TEXT_SIGNATURE, new MockUserObtainUnauthorizedRequestToken()).includeVersion(true)
                        .callback(CALLBACK)
                        .additionalParams(HeaderFields.of(Set.of(HeaderField.of(SERVICE_PROVIDER_PARAM_1, SPP_1_VAL))))
                        .build());
        Assertions.assertNotNull(oauthFlow);
        LOG.info(oauthFlow);

        /* enter the phase for acquiring the request token */
        RequestTokenFlow requestTokenFlow = oauthFlow.requestTokenFlow();
        Assertions.assertNotNull(requestTokenFlow);
        LOG.info(requestTokenFlow);

        /* obtain the request token */
        UserAuthorizationFlowFactory userAuthorizationFlowFactory = requestTokenFlow.obtainRequestToken();
        Assertions.assertNotNull(userAuthorizationFlowFactory);
        LOG.info(userAuthorizationFlowFactory);

        /* enter the phase for performing the user authorization */
        UserAuthorizationFlow userAuthorizationFlow = userAuthorizationFlowFactory
                .userAuthorizationFlow(new MockUserAuthorizationFlowFactoryParam());
        Assertions.assertNotNull(userAuthorizationFlow);
        LOG.info(userAuthorizationFlow);

        /* acquire the user authorization */
        AccessTokenFlowFactory accessTokenFlowFactory = userAuthorizationFlow.userAuthorization();
        Assertions.assertNotNull(accessTokenFlowFactory);
        LOG.info(accessTokenFlowFactory);

        /* enter the token exchange phase */
        AccessTokenFlow accessTokenFlow = accessTokenFlowFactory.accessTokenFlow(new MockAccessTokenFlowFactoryParam());
        Assertions.assertNotNull(accessTokenFlow);
        LOG.info(accessTokenFlow);

        /* execute the token exchange */
        ProtectedAccessFlowFactory protectedAccessFlowFactory = accessTokenFlow.tokenExchange();
        Assertions.assertNotNull(protectedAccessFlowFactory);
        LOG.info(protectedAccessFlowFactory);

        /* retrieve the protected access */
        ProtectedAccess protectedAccess = protectedAccessFlowFactory
                .protectedAccess(new MockProtectedAccessFlowFactoryParam());
        Assertions.assertNotNull(protectedAccess);
        LOG.info(protectedAccess);

        /* build the header fields for accessing protected resources */
        HeaderFields headerFields = protectedAccess.headerFields();
        Assertions.assertNotNull(headerFields);
        LOG.info(headerFields);

        /* check for equality */
        Set<HeaderField> equalityCheck = headerFields.fields().stream().map(ComparingHeaderField::new)
                .collect(Collectors.toSet());
        Assertions.assertTrue(equalityCheck
                .contains(new ComparingHeaderField(HeaderField.of(OAUTH_CONSUMER_KEY_KEY, CONSUMER_KEY.get()))));
        Assertions.assertTrue(
                equalityCheck.contains(new ComparingHeaderField(HeaderField.of(OAUTH_TOKEN_KEY, ACCESS_TOKEN.get()))));
        Assertions.assertTrue(equalityCheck.contains(new ComparingHeaderField(HeaderField.of(OAUTH_SIGNATURE_METHOD_KEY,
                HMAC_SHA1_SIGNATURE_FOR_PROTECTED_RESOURCE_ACCESS.oAuthName().get()))));
        Assertions.assertTrue(equalityCheck
                .contains(new ComparingHeaderField(HeaderField.of(OAUTH_VERSION_KEY, OAUTH_VERSION_NUMBER))));
        Assertions.assertTrue(
                equalityCheck.contains(new ComparingHeaderField(HeaderField.of(PROT_RES_HV_1_KEY, PROT_RES_HV_1_VAL))));
        Assertions.assertTrue(
                equalityCheck.contains(new ComparingHeaderField(HeaderField.of(PROT_RES_HV_2_KEY, PROT_RES_HV_2_VAL))));

        /* check for presence */
        Set<HeaderField> existenceCheck = headerFields.fields().stream().map(ComparingKeyHeaderField::new)
                .collect(Collectors.toSet());
        Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_SIGNATURE_KEY)));
        Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_TIMESTAMP_KEY)));
        Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_NONCE_KEY)));
    }

    private static final class MockUserObtainUnauthorizedRequestToken implements UserObtainRequestToken {
        @Override
        public UserRequestToken requestToken(HeaderFields headerParams) {
            /* log for visual tracking */
            LOG.info(headerParams);
            /* wrap for equality checking */
            Set<ComparingHeaderField> equalityCheck = headerParams.fields().stream().map(ComparingHeaderField::new)
                    .collect(Collectors.toSet());

            /* check for equality */
            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(OAUTH_CONSUMER_KEY_KEY, CONSUMER_KEY.get()))));
            Assertions.assertTrue(equalityCheck.contains(new ComparingHeaderField(
                    HeaderField.of(OAUTH_SIGNATURE_METHOD_KEY, PLAIN_TEXT_SIGNATURE.oAuthName().get()))));
            Assertions.assertTrue(equalityCheck.contains(
                    new ComparingHeaderField(HeaderField.of(OAUTH_SIGNATURE_KEY, EXPECTED_PLAIN_TEXT_SIGNATURE))));
            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(OAUTH_VERSION_KEY, OAUTH_VERSION_NUMBER))));
            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(OAUTH_CALLBACK_KEY, CALLBACK.toString()))));
            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(SERVICE_PROVIDER_PARAM_1, SPP_1_VAL))));

            /* check for existence */
            Set<ComparingKeyHeaderField> existenceCheck = headerParams.fields().stream()
                    .map(ComparingKeyHeaderField::new).collect(Collectors.toSet());
            Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_TIMESTAMP_KEY)));
            Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_NONCE_KEY)));

            /* return mock result */
            return new MockUserUnauthorizedRequestToken();
        }
    }

    private static final class MockUserUnauthorizedRequestToken implements UserRequestToken {
        @Override
        public RequestToken token() {
            return REQUEST_TOKEN;
        }

        @Override
        public RequestTokenSecret tokenSecret() {
            return REQUEST_TOKEN_SECRET;
        }

        @Override
        public boolean callback() {
            return true;
        }
    }

    private static final class ComparingHeaderField implements HeaderField {
        private final HeaderField hf;

        ComparingHeaderField(HeaderField hf) {
            this.hf = hf;
        }

        @Override
        public String key() {
            return this.hf.key();
        }

        @Override
        public Optional<String> value() {
            return this.hf.value();
        }

        @Override
        public int hashCode() {
            return this.hf.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HeaderField)) {
                return false;
            }
            HeaderField other = (HeaderField) obj;
            return new EqualsBuilder().append(this.hf.key(), other.key()).append(this.hf.value(), other.value())
                    .isEquals();
        }
    }

    private static final class ComparingKeyHeaderField implements HeaderField {
        private final String key;

        ComparingKeyHeaderField(HeaderField hf) {
            this(hf.key());
        }

        ComparingKeyHeaderField(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return this.key;
        }

        @Override
        public Optional<String> value() {
            return Optional.empty();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(this.key).toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HeaderField)) {
                return false;
            }
            HeaderField other = (HeaderField) obj;
            return new EqualsBuilder().append(this.key, other.key()).isEquals();
        }
    }

    private static final class MockUserAuthorizationFlowFactoryParam implements UserAuthorizationFlowFactoryParam {
        @Override
        public UserAuthorization userAuthorization() {
            return new MockUserAuthorization();
        }
    }

    private static final class MockUserAuthorization implements UserAuthorization {
        @Override
        public VerificationCode verificationCode(RequestToken requestToken) {
            Assertions.assertEquals(REQUEST_TOKEN, requestToken);
            return VERIFICATION_CODE;
        }
    }

    private static final class MockAccessTokenFlowFactoryParam implements AccessTokenFlowFactoryParam {
        @Override
        public SignatureMethod signatureMethod() {
            return HMAC_SHA1_SIGNATURE_FOR_ACCESS_TOKEN;
        }

        @Override
        public UserObtainAccessToken userObtainAccessToken() {
            return new MockUserObtainAccessToken();
        }
    }

    private static final class MockUserObtainAccessToken implements UserObtainAccessToken {
        @Override
        public UserAccessTokenParameter obtainAccessToken(HeaderFields requestHeaderParameters) {
            /* log for visual tracking */
            Assertions.assertNotNull(requestHeaderParameters);
            LOG.info(requestHeaderParameters);
            /* wrap for equality check */
            Set<HeaderField> equalityCheck = requestHeaderParameters.fields().stream().map(ComparingHeaderField::new)
                    .collect(Collectors.toSet());

            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(OAUTH_CONSUMER_KEY_KEY, CONSUMER_KEY.get()))));
            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(OAUTH_TOKEN_KEY, REQUEST_TOKEN.get()))));
            Assertions.assertTrue(equalityCheck.contains(new ComparingHeaderField(HeaderField
                    .of(OAUTH_SIGNATURE_METHOD_KEY, HMAC_SHA1_SIGNATURE_FOR_ACCESS_TOKEN.oAuthName().get()))));
            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(OAUTH_VERSION_KEY, OAUTH_VERSION_NUMBER))));
            Assertions.assertTrue(equalityCheck
                    .contains(new ComparingHeaderField(HeaderField.of(OAUTH_VERIFIER_KEY, VERIFICATION_CODE.get()))));
            /* wrap for existence check */
            Set<HeaderField> existenceCheck = requestHeaderParameters.fields().stream()
                    .map(ComparingKeyHeaderField::new).collect(Collectors.toSet());
            Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_TIMESTAMP_KEY)));
            Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_NONCE_KEY)));
            Assertions.assertTrue(existenceCheck.contains(new ComparingKeyHeaderField(OAUTH_SIGNATURE_KEY)));
            /* return mock result */
            return new MockUserAccessTokenParameter();
        }
    }

    private static final class MockUserAccessTokenParameter implements UserAccessTokenParameter {
        @Override
        public AccessToken accessToken() {
            return ACCESS_TOKEN;
        }

        @Override
        public AccessTokenSecret accessTokenSecret() {
            return ACCESS_TOKEN_SECRET;
        }
    }

    private static final class MockProtectedAccessFlowFactoryParam implements ProtectedAccessFlowFactoryParam {
        @Override
        public SignatureMethod signatureMethod() {
            return HMAC_SHA1_SIGNATURE_FOR_PROTECTED_RESOURCE_ACCESS;
        }

        @Override
        public HeaderFields additionalParams() {
            return HeaderFields.of(Set.of(HeaderField.of(PROT_RES_HV_1_KEY, PROT_RES_HV_1_VAL),
                    HeaderField.of(PROT_RES_HV_2_KEY, PROT_RES_HV_2_VAL)));
        }
    }
}
