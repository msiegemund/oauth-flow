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
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.msiegemund.oauth.flow.params.ConsumerSecret;
import io.github.msiegemund.oauth.flow.params.RequestTokenSecret;
import io.github.msiegemund.oauth.flow.params.Signature;
import io.github.msiegemund.oauth.flow.signature.params.PlainTextSignatureParams;

final class DefaultPlainTextSignatureTest {

    private static final Log LOG = LogFactory.getLog(DefaultPlainTextSignatureTest.class);

    private static final ConsumerSecret CONSUMER_SECRET = ConsumerSecret.of("djr9rjt0jd78jf88");
    private static final String TOKEN_SECRET_1 = "jjd999tj88uiths3";
    private static final String TOKEN_SECRET_1_SIG = "djr9rjt0jd78jf88%26jjd999tj88uiths3";
    private static final String TOKEN_SECRET_2 = "jjd99$tj88uiths3";
    private static final String TOKEN_SECRET_2_SIG = "djr9rjt0jd78jf88%26jjd99%2524tj88uiths3";
    private static final String TOKEN_SECRET_3 = null;
    private static final String TOKEN_SECRET_3_SIG = "djr9rjt0jd78jf88%26";

    @ParameterizedTest
    @MethodSource("args")
    void test(String tokenSecret, String expectedSig) {
        Signature actualSignature = new DefaultPlainTextSignature(CONSUMER_SECRET)
                .signature(new PlainTextSignatureParams.DefaultPlainTextSignatureParams(
                        Optional.ofNullable(tokenSecret).map(RequestTokenSecret::of).orElse(null)));
        Assertions.assertNotNull(actualSignature);
        LOG.info("plain: " + actualSignature);
        actualSignature = Signature.of(URLEncoder.encode(actualSignature.get(), StandardCharsets.UTF_8));
        LOG.info("encoded: " + actualSignature);
        Assertions.assertEquals(expectedSig, actualSignature.get());
    }

    private static Stream<Arguments> args() {
        return Stream.of(Arguments.of(TOKEN_SECRET_1, TOKEN_SECRET_1_SIG),
                Arguments.of(TOKEN_SECRET_2, TOKEN_SECRET_2_SIG), Arguments.of(TOKEN_SECRET_3, TOKEN_SECRET_3_SIG));
    }
}
