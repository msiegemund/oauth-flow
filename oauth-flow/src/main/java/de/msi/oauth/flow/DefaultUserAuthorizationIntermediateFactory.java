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

final class DefaultUserAuthorizationIntermediateFactory implements UserAuthorizationIntermediateFactory {
    private final UserAuthorizationFlowParam uafp;

    DefaultUserAuthorizationIntermediateFactory(UserAuthorizationFlowParam uafp) {
        this.uafp = Objects.requireNonNull(uafp, "the UserAuthorizationFlowParam is mandatory");
    }

    @Override
    public UserAuthorizationFlow userAuthorizationFlow() {
        return new DefaultUserAuthorizationFlow(this.uafp);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.uafp).build();
    }
}
