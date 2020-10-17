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

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.msiegemund.oauth.flow.params.ConsumerKey;
import io.github.msiegemund.oauth.flow.user.UserAuthorization;
import io.github.msiegemund.oauth.flow.user.UserRequestToken;

final class DefaultUserAuthorizationFlowFactory implements UserAuthorizationFlowFactory {
    private final RequestTokenFlowParam rtfp;
    private final UserRequestToken uurt;

    DefaultUserAuthorizationFlowFactory(RequestTokenFlowParam rtfp, UserRequestToken uurt) {
        this.rtfp = Objects.requireNonNull(rtfp, "the RequestTokenFlowParam is mandatory");
        this.uurt = Objects.requireNonNull(uurt, "the UserUnauthorizedRequestToken is mandatory");
    }

    @Override
    public UserAuthorizationFlow userAuthorizationFlow(UserAuthorizationFlowFactoryParam uafp) {
        return new DefaultUserAuthorizationFlow(new DefaultUserAuthorizationFlowParam(uafp));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.rtfp).append(this.uurt).build();
    }

    private final class DefaultUserAuthorizationFlowParam implements UserAuthorizationFlowParam {
        private final UserAuthorizationFlowFactoryParam uaffp;

        DefaultUserAuthorizationFlowParam(UserAuthorizationFlowFactoryParam uafp) {
            this.uaffp = Objects.requireNonNull(uafp, "the UserAuthorizationFlowFactoryParam is mandatory");
        }

        @Override
        public ConsumerKey consumerKey() {
            return DefaultUserAuthorizationFlowFactory.this.rtfp.consumerKey();
        }

        @Override
        public boolean includeVersion() {
            return DefaultUserAuthorizationFlowFactory.this.rtfp.includeVersion();
        }

        @Override
        public UserRequestToken userUnauthorizedRequestToken() {
            return DefaultUserAuthorizationFlowFactory.this.uurt;
        }

        @Override
        public UserAuthorization userAuthorization() {
            return this.uaffp.userAuthorization();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.uaffp).build();
        }
    }
}
