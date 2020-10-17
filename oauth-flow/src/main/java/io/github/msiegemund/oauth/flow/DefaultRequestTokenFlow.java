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
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.msiegemund.oauth.flow.params.header.HeaderFieldsBuilder;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderFields;
import io.github.msiegemund.oauth.flow.user.UserRequestToken;

final class DefaultRequestTokenFlow implements RequestTokenFlow {
    private final RequestTokenFlowParam rtfp;

    DefaultRequestTokenFlow(RequestTokenFlowParam rtfp) {
        this.rtfp = Objects.requireNonNull(rtfp, "the RequestTokenFlowParam is mandatory");
    }

    @Override
    public UserAuthorizationFlowFactory obtainRequestToken() {
        HeaderFieldsBuilder hpb = headerParameterBuilder();
        /* add optional version information */
        if (this.rtfp.includeVersion()) {
            hpb.versionInformation();
        }
        /* optionally add additional parameters */
        Optional.ofNullable(this.rtfp.additionalParams()).ifPresent(hpb::additionalParameter);
        /* build the header params */
        HeaderFields headerParams = hpb.consumerKey(this.rtfp.consumerKey()).build(this.rtfp.signatureMethod());
        /* invoke user functionality to get request token */
        UserRequestToken uurt = this.rtfp.userObtainRequestToken().requestToken(headerParams);
        /* check result for callback success */
        if (!uurt.callback()) {
            /*
             * TODO remove this check and remove the callback information from the interface? -> maybe user should
             * handle this within the userObtainRequestToken operation
             */
            throw new IllegalStateException("callback failure received");
        }
        /* return net flow phase */
        return new DefaultUserAuthorizationFlowFactory(this.rtfp, uurt);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.rtfp).build();
    }

    /**
     * Creates a new {@link HeaderFieldsBuilder} instance in regard to the
     * given callback.
     * 
     * @return a new instance
     */
    private HeaderFieldsBuilder headerParameterBuilder() {
        return this.rtfp.callback().map(HeaderFieldsBuilder::withCallback)
                .orElseGet(HeaderFieldsBuilder::withOutOfBandsCallback);
    }
}
