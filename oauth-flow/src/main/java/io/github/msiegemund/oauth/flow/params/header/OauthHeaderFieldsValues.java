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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Provides mandatory fields building <code>OAuth</code> header fields.
 *
 * @author Martin Siegemund
 */
interface OauthHeaderFieldsValues {
    /**
     * Retrieve the value for <code>oauth_timestamp</code>.
     * 
     * @return the value
     */
    String timestamp();

    /**
     * Retrieve the value for <code>oauth_nonce</code>.
     * 
     * @return the value
     */
    String nonce();

    /**
     * The default implementation of the {@link OauthHeaderFieldsValues}.
     *
     * @author Martin Siegemund
     */
    static final class DefaultOauthHeaderFieldsValues implements OauthHeaderFieldsValues {
        private static final int LOWER_LIMIT = 0x61;
        private static final int UPPER_LIMIT = 0x7A;
        private static final int NONCE_BYTES = 8;

        @Override
        public String timestamp() {
            return Long.toString(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        }

        @Override
        public String nonce() {
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            byte[] rnd = new byte[NONCE_BYTES];
            for (int i = 0; i < NONCE_BYTES; ++i) {
                rnd[i] = (byte) threadLocalRandom.nextInt(LOWER_LIMIT, UPPER_LIMIT);
            }
            return URLEncoder.encode(new String(rnd, StandardCharsets.US_ASCII), StandardCharsets.US_ASCII);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).build();
        }
    }
}
