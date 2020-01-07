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
 * Requests the user authorization by invoking {@link #userAuthorization()} and returns the flows next phase, the
 * {@link AccessTokenFlowFactory}.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step2">6.2. Obtaining User Authorization</a>
 */
@FunctionalInterface
public interface UserAuthorizationFlow {
    /**
     * Request the user authorization and retrieve the next phase of the flow.
     * 
     * @return the {@link AccessTokenFlowFactory}
     */
    AccessTokenFlowFactory userAuthorization();
}
