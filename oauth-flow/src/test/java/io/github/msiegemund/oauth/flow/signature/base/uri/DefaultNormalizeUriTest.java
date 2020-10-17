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
package io.github.msiegemund.oauth.flow.signature.base.uri;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.msiegemund.oauth.flow.signature.base.uri.DefaultNormalizeUri;

final class DefaultNormalizeUriTest {

    @ParameterizedTest
    @MethodSource("testArgs")
    void test(String input, String output) {
        Assertions.assertEquals(output, new DefaultNormalizeUri(URI.create(input)).normalize());
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(Arguments.of("HTTP://Example.com:80/resource?id=123", "http://example.com/resource"),
                Arguments.of("HTTP://Example.com:443/resource?id=123", "http://example.com:443/resource"),
                Arguments.of("HTTP://Example.com:1234/resource?id=123", "http://example.com:1234/resource"),
                Arguments.of("HTTPS://Example.com:443/resource/res2?id=123", "https://example.com/resource/res2"));
    }
}
