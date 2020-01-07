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

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.msi.oauth.flow.params.ConsumerKey;
import de.msi.oauth.flow.params.header.fields.HeaderFields;
import de.msi.oauth.flow.signature.SignatureMethod;
import de.msi.oauth.flow.user.UserObtainRequestToken;

final class DefaultRequestTokenFlowParam implements RequestTokenFlowParam {

    private final ConsumerKey consumerKey;
    private final SignatureMethod signatureMethod;
    private final boolean includeVersion;
    private final URI callback;
    private final HeaderFields additionalParams;
    private final UserObtainRequestToken userObtainUnauthorizedRequestToken;

    DefaultRequestTokenFlowParam(ConsumerKey ck, SignatureMethod sm, boolean iv, URI c, HeaderFields ap,
            UserObtainRequestToken uourt) {
        this.consumerKey = Objects.requireNonNull(ck, "the consumer key is mandatory");
        this.signatureMethod = Objects.requireNonNull(sm, "the SignatureMethod is mandatory");
        this.includeVersion = iv;
        this.callback = c;
        this.additionalParams = Objects.requireNonNull(ap, "the HeaderFields instance is mandatory");
        this.userObtainUnauthorizedRequestToken = Objects.requireNonNull(uourt,
                "the UserObtainUnauthorizedRequestToken instance is mandatory");
    }

    @Override
    public ConsumerKey consumerKey() {
        return this.consumerKey;
    }

    @Override
    public SignatureMethod signatureMethod() {
        return this.signatureMethod;
    }

    @Override
    public boolean includeVersion() {
        return this.includeVersion;
    }

    @Override
    public Optional<URI> callback() {
        return Optional.ofNullable(this.callback);
    }

    @Override
    public HeaderFields additionalParams() {
        return this.additionalParams;
    }

    @Override
    public UserObtainRequestToken userObtainRequestToken() {
        return this.userObtainUnauthorizedRequestToken;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.consumerKey)
                .append(this.signatureMethod).append("includeVersion", this.includeVersion)
                .append("callback", this.callback).append("additionalParams", this.additionalParams)
                .append(this.userObtainUnauthorizedRequestToken).build();
    }
}
