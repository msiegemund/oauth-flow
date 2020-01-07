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

/**
 * Builds {@link UserAuthorizationFlow} instances by accepting the required {@link UserAuthorizationFlowParam}.
 * <p>
 * This logic is skipping flow phases prior to this one.
 * <p>
 * Use {@link #of(UserAuthorizationFlowParam)} to create a new instance.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step2">6.2. Obtaining User Authorization</a>
 */
@FunctionalInterface
public interface UserAuthorizationIntermediateFactory {
    /**
     * Create a new {@link UserAuthorizationFlow}.
     * 
     * @return the new instance
     */
    UserAuthorizationFlow userAuthorizationFlow();

    /**
     * Create a new instance of the {@link UserAuthorizationIntermediateFactory}.
     * 
     * @param uafp the mandatory {@link UserAuthorizationFlowParam}
     * @return the new instance
     */
    static UserAuthorizationIntermediateFactory of(UserAuthorizationFlowParam uafp) {
        return new DefaultUserAuthorizationIntermediateFactory(uafp);
    }
}
