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
package io.github.msiegemund.oauth.flow.signature.base;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.msiegemund.oauth.flow.params.HttpMethod;
import io.github.msiegemund.oauth.flow.params.SignatureBaseString;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderField;
import io.github.msiegemund.oauth.flow.signature.base.DefaultSignatureBaseStringBuilder;

final class SignatureBaseStringBuilderTest {
    private static final String URL = "http://photos.example.net/photos";
    private static final String EXPECTED = "GET&http%3A%2F%2Fphotos.example.net%2Fphotos&file%3Dvacation.jpg%26oauth_consumer_key%3Ddpf43f3p2l4k3l03%26oauth_nonce%3Dkllo9940pd9333jh%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1191242096%26oauth_token%3Dnnch734d00sl2jdk%26oauth_version%3D1.0%26size%3Doriginal";
    private static final Log LOG = LogFactory.getLog(SignatureBaseStringBuilderTest.class);

    @Test
    void test() {
        SignatureBaseString baseString = new DefaultSignatureBaseStringBuilder(HttpMethod.GET, URI.create(URL))
                .add(HeaderField.of("oauth_consumer_key", "dpf43f3p2l4k3l03"))
                .add(HeaderField.of("oauth_token", "nnch734d00sl2jdk"))
                .add(HeaderField.of("oauth_signature_method", "HMAC-SHA1"))
                .add(HeaderField.of("oauth_timestamp", "1191242096"))
                .add(HeaderField.of("oauth_nonce", "kllo9940pd9333jh")).add(HeaderField.of("oauth_version", "1.0"))
                .add(HeaderField.of("file", "vacation.jpg")).add(HeaderField.of("size", "original")).build();
        LOG.info(baseString);
        Assertions.assertEquals(EXPECTED, baseString.get());
    }
}
