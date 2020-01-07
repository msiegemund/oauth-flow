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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.msi.oauth.flow.params.ConsumerSecret;
import de.msi.oauth.flow.params.HttpMethod;
import de.msi.oauth.flow.params.RequestTokenSecret;
import de.msi.oauth.flow.params.Signature;
import de.msi.oauth.flow.params.SignatureBaseString;
import de.msi.oauth.flow.signature.params.SignedTextSignatureParams;

final class HmacSha1SignatureTest {
    private static final Log LOG = LogFactory.getLog(HmacSha1SignatureTest.class);

    @Test
    void test() {
        HmacSha1Signature hmacSha1Signature = new HmacSha1Signature.DefaultHmacSha1Signature(
                URI.create("http://photos.example.net/photos"), HttpMethod.GET, ConsumerSecret.of("kd94hf93k423kf44"));
        LOG.info(hmacSha1Signature);

        Signature signature = hmacSha1Signature
                .signature(new SignedTextSignatureParams.DefaultSignedTextSignatureParams(SignatureBaseString.of(
                        "GET&http%3A%2F%2Fphotos.example.net%2Fphotos&file%3Dvacation.jpg%26oauth_consumer_key%3Ddpf43f3p2l4k3l03%26oauth_nonce%3Dkllo9940pd9333jh%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1191242096%26oauth_token%3Dnnch734d00sl2jdk%26oauth_version%3D1.0%26size%3Doriginal"),
                        RequestTokenSecret.of("pfkkdhi9sl3r4s00")));

        LOG.info(signature);
        Assertions.assertEquals("tR3+Ty81lMeYAr/Fid0kMTYa/WM=", signature.get());
    }
}
