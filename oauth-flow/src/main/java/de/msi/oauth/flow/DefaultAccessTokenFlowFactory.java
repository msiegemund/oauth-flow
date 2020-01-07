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

package de.msi.oauth.flow;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.msi.oauth.flow.params.ConsumerKey;
import de.msi.oauth.flow.params.VerificationCode;
import de.msi.oauth.flow.signature.SignatureMethod;
import de.msi.oauth.flow.user.UserObtainAccessToken;
import de.msi.oauth.flow.user.UserRequestToken;

final class DefaultAccessTokenFlowFactory implements AccessTokenFlowFactory {
    private final UserAuthorizationFlowParam uafp;
    private final VerificationCode verificationCode;

    DefaultAccessTokenFlowFactory(UserAuthorizationFlowParam uafp, VerificationCode verificationCode) {
        this.uafp = Objects.requireNonNull(uafp, "the UserAuthorizationFlowParam is mandatory");
        this.verificationCode = Objects.requireNonNull(verificationCode, "the verificationCode is mandatory");
    }

    @Override
    public AccessTokenFlow accessTokenFlow(AccessTokenFlowFactoryParam atfp) {
        return new DefaultAccessTokenFlow(new DefaultAccessTokenFlowParamComplete(atfp));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.uafp)
                .append(this.verificationCode).build();
    }

    private final class DefaultAccessTokenFlowParamComplete implements AccessTokenFlowParam {
        private final AccessTokenFlowFactoryParam atfp;

        DefaultAccessTokenFlowParamComplete(AccessTokenFlowFactoryParam atfp) {
            this.atfp = Objects.requireNonNull(atfp, "the AccessTokenFlowFactoryParam is mandatory");
        }

        @Override
        public ConsumerKey consumerKey() {
            return DefaultAccessTokenFlowFactory.this.uafp.consumerKey();
        }

        @Override
        public boolean includeVersion() {
            return DefaultAccessTokenFlowFactory.this.uafp.includeVersion();
        }

        @Override
        public UserRequestToken userUnauthorizedRequestToken() {
            return DefaultAccessTokenFlowFactory.this.uafp.userUnauthorizedRequestToken();
        }

        @Override
        public VerificationCode verificationCode() {
            return DefaultAccessTokenFlowFactory.this.verificationCode;
        }

        @Override
        public SignatureMethod signatureMethod() {
            return this.atfp.signatureMethod();
        }

        @Override
        public UserObtainAccessToken userObtainAccessToken() {
            return this.atfp.userObtainAccessToken();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.atfp).build();
        }
    }
}
