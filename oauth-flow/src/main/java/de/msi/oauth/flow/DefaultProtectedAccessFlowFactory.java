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
import de.msi.oauth.flow.params.header.fields.HeaderFields;
import de.msi.oauth.flow.signature.SignatureMethod;
import de.msi.oauth.flow.user.UserAccessTokenParameter;

final class DefaultProtectedAccessFlowFactory implements ProtectedAccessFlowFactory {
    private final AccessTokenFlowParam atfp;
    private final UserAccessTokenParameter uatp;

    DefaultProtectedAccessFlowFactory(AccessTokenFlowParam atfp, UserAccessTokenParameter uatp) {
        this.atfp = Objects.requireNonNull(atfp, "the AccessTokenFlowParam is mandatory");
        this.uatp = Objects.requireNonNull(uatp, "the UserAccessTokenParameter is mandatory");
    }

    @Override
    public ProtectedAccess protectedAccess(ProtectedAccessFlowFactoryParam paffp) {
        return new DefaultProtectedAccess(new DefaultProtectedAccessParam(paffp));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.atfp).append(this.uatp).build();
    }

    private final class DefaultProtectedAccessParam implements ProtectedAccessParam {
        private final ProtectedAccessFlowFactoryParam paffp;

        DefaultProtectedAccessParam(ProtectedAccessFlowFactoryParam paffp) {
            this.paffp = Objects.requireNonNull(paffp, "the ProtectedAccessFlowFactoryParam is mandatory");
        }

        @Override
        public ConsumerKey consumerKey() {
            return DefaultProtectedAccessFlowFactory.this.atfp.consumerKey();
        }

        @Override
        public SignatureMethod signatureMethod() {
            return this.paffp.signatureMethod();
        }

        @Override
        public boolean includeVersion() {
            return DefaultProtectedAccessFlowFactory.this.atfp.includeVersion();
        }

        @Override
        public HeaderFields additionalParams() {
            return this.paffp.additionalParams();
        }

        @Override
        public UserAccessTokenParameter userAccessTokenParameter() {
            return DefaultProtectedAccessFlowFactory.this.uatp;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.paffp).build();
        }
    }
}
