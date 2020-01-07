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
package de.msi.oauth.flow.params.header;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.msi.oauth.flow.params.ConsumerKey;
import de.msi.oauth.flow.params.ConsumerSecret;
import de.msi.oauth.flow.params.HttpMethod;
import de.msi.oauth.flow.params.RequestToken;
import de.msi.oauth.flow.params.RequestTokenSecret;
import de.msi.oauth.flow.params.header.fields.HeaderField;
import de.msi.oauth.flow.params.header.fields.HeaderFields;
import de.msi.oauth.flow.signature.HmacSha1Signature;

final class DefaultHeaderParameterBuilderTest {

    private static final Log LOG = LogFactory.getLog(DefaultHeaderParameterBuilderTest.class);

    private static final String REALM_KEY = "realm";
    private static final String REALM_VAL = "http://photos.example.net/";
    private static final String SIZE_VAL = "original";
    private static final String SIZE_KEY = "size";
    private static final String FILE_VAL = "vacation.jpg";
    private static final String FILE_KEY = "file";
    private static final RequestTokenSecret TOKEN_SECRET = RequestTokenSecret.of("pfkkdhi9sl3r4s00");
    private static final RequestToken TOKEN = RequestToken.of("nnch734d00sl2jdk");
    private static final ConsumerKey CONSUMER_KEY = ConsumerKey.of("dpf43f3p2l4k3l03");
    private static final URI ENDPOINT = URI.create("http://photos.example.net/photos");
    private static final HttpMethod METHOD = HttpMethod.GET;
    private static final ConsumerSecret CONSUMER_SECRET = ConsumerSecret.of("kd94hf93k423kf44");
    private static final String NONCE = "kllo9940pd9333jh";
    private static final String TIMESTAMP = "1191242096";

    @Test
    void test() {
        /* create all header values */
        HeaderFields headerParameters = new DefaultHeaderFieldsBuilder(new MockOauthHeaderParameterValues(),
                new CallbackParameter.NoCallback()).consumerKey(CONSUMER_KEY).versionInformation().token(TOKEN)
                        .tokenSecret(TOKEN_SECRET).additionalParameter(additionalParams())
                        .build(new HmacSha1Signature.DefaultHmacSha1Signature(ENDPOINT, METHOD, CONSUMER_SECRET));
        /* check for expected values */
        LOG.info(headerParameters);
        Set<HeaderField> parameters = new HashSet<>(headerParameters.fields());
        Assertions.assertNotNull(parameters);
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField("oauth_consumer_key", CONSUMER_KEY.get())));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField("oauth_signature_method", "HMAC-SHA1")));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField("oauth_timestamp", TIMESTAMP)));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField("oauth_nonce", NONCE)));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField("oauth_version", "1.0")));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField("oauth_token", TOKEN.get())));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField(FILE_KEY, FILE_VAL)));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField(SIZE_KEY, SIZE_VAL)));
        Assertions.assertTrue(parameters.contains(new OrderedHeaderField(REALM_KEY, REALM_VAL)));
        Assertions.assertTrue(
                parameters.contains(new OrderedHeaderField("oauth_signature", "tR3%2BTy81lMeYAr%2FFid0kMTYa%2FWM%3D")));
    }

    private static HeaderFields additionalParams() {
        return HeaderFields.of(Set.of(HeaderField.of(FILE_KEY, FILE_VAL), HeaderField.of(SIZE_KEY, SIZE_VAL),
                HeaderField.of(REALM_KEY, REALM_VAL)));
    }

    private static final class MockOauthHeaderParameterValues implements OauthHeaderFieldsValues {
        @Override
        public String timestamp() {
            return TIMESTAMP;
        }

        @Override
        public String nonce() {
            return NONCE;
        }
    }
}
