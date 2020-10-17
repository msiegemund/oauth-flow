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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.msiegemund.oauth.flow.UserAuthorizationFlow;
import io.github.msiegemund.oauth.flow.UserAuthorizationFlowParam;
import io.github.msiegemund.oauth.flow.UserAuthorizationIntermediateFactory;
import io.github.msiegemund.oauth.flow.params.ConsumerKey;
import io.github.msiegemund.oauth.flow.params.RequestToken;
import io.github.msiegemund.oauth.flow.params.RequestTokenSecret;
import io.github.msiegemund.oauth.flow.params.VerificationCode;
import io.github.msiegemund.oauth.flow.user.UserAuthorization;
import io.github.msiegemund.oauth.flow.user.UserRequestToken;

final class UserAuthorizationIntermediateFactoryTest {
    private static final Log LOG = LogFactory.getLog(UserAuthorizationIntermediateFactoryTest.class);
    private static final ConsumerKey CONSUMER_KEY = ConsumerKey.of("dpf43f3p2l4k3l03");
    private static final VerificationCode VERIFICATION_CODE = VerificationCode.of("hfdp7dh39dks9884");
    private static final RequestTokenSecret REQUEST_TOKEN_SECRET = RequestTokenSecret.of("hdhd0244k9j7ao03");
    private static final RequestToken REQUEST_TOKEN = RequestToken.of("hh5s93j4hdidpola");

    @Test
    void test() {
        UserAuthorizationIntermediateFactory userAuthorizationIntermediateFactory = UserAuthorizationIntermediateFactory
                .of(new MockUserAuthorizationFlowParam());
        Assertions.assertNotNull(userAuthorizationIntermediateFactory);
        LOG.info(userAuthorizationIntermediateFactory);

        UserAuthorizationFlow userAuthorizationFlow = userAuthorizationIntermediateFactory.userAuthorizationFlow();
        Assertions.assertNotNull(userAuthorizationFlow);
        LOG.info(userAuthorizationFlow);
    }

    private static final class MockUserAuthorizationFlowParam implements UserAuthorizationFlowParam {
        @Override
        public UserAuthorization userAuthorization() {
            return new MockUserAuthorization();
        }

        @Override
        public ConsumerKey consumerKey() {
            return CONSUMER_KEY;
        }

        @Override
        public boolean includeVersion() {
            return false;
        }

        @Override
        public UserRequestToken userUnauthorizedRequestToken() {
            return new MockUserRequestToken();
        }
    }

    private static final class MockUserAuthorization implements UserAuthorization {
        @Override
        public VerificationCode verificationCode(@SuppressWarnings("unused") RequestToken requestToken) {
            return VERIFICATION_CODE;
        }
    }

    private static final class MockUserRequestToken implements UserRequestToken {
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
            return false;
        }
    }
}
