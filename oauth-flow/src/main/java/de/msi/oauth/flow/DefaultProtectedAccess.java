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
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.msi.oauth.flow.params.header.HeaderFieldsBuilder;
import de.msi.oauth.flow.params.header.fields.HeaderFields;

final class DefaultProtectedAccess implements ProtectedAccess {
    private final ProtectedAccessParam pap;

    DefaultProtectedAccess(ProtectedAccessParam pap) {
        this.pap = Objects.requireNonNull(pap, "the ProtectedAccessParam is mandatory");
    }

    @Override
    public HeaderFields headerFields() {
        /* create the header parameter builder */
        HeaderFieldsBuilder hpb = HeaderFieldsBuilder.withoutCallback();
        /* optionally add version parameter info */
        if (this.pap.includeVersion()) {
            hpb.versionInformation();
        }
        /* optionally add additional parameters */
        Optional.ofNullable(this.pap.additionalParams()).ifPresent(hpb::additionalParameter);
        /* build the parameters */
        return hpb.consumerKey(this.pap.consumerKey()).token(this.pap.userAccessTokenParameter().accessToken())
                .tokenSecret(this.pap.userAccessTokenParameter().accessTokenSecret()).build(this.pap.signatureMethod());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.pap).build();
    }
}
