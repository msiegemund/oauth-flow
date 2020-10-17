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

import io.github.msiegemund.oauth.flow.params.header.HeaderFieldsBuilder;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderFields;

final class DefaultAccessTokenFlow implements AccessTokenFlow {
    private final AccessTokenFlowParam atfp;

    DefaultAccessTokenFlow(AccessTokenFlowParam atfp) {
        this.atfp = Objects.requireNonNull(atfp, "the AccessTokenFlowParam is mandatory");
    }

    @Override
    public ProtectedAccessFlowFactory tokenExchange() {
        /* create header builder */
        HeaderFieldsBuilder hpb = HeaderFieldsBuilder.withoutCallback();
        /* optionally add the version information */
        if (this.atfp.includeVersion()) {
            hpb.versionInformation();
        }
        /* create the required header fields for obtaining the access token */
        HeaderFields headerFields = hpb.consumerKey(this.atfp.consumerKey())
                .token(this.atfp.userUnauthorizedRequestToken().token())
                .tokenSecret(this.atfp.userUnauthorizedRequestToken().tokenSecret())
                .verifier(this.atfp.verificationCode()).build(this.atfp.signatureMethod());
        /*
         * obtain the access token and return the final factory for creating
         * authorized access headers
         */
        return new DefaultProtectedAccessFlowFactory(this.atfp,
                this.atfp.userObtainAccessToken().obtainAccessToken(headerFields));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.atfp).build();
    }
}
