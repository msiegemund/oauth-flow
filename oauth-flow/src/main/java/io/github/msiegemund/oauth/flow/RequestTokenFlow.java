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

/**
 * This flows phase is capable of obtaining the request token by invoking {@link #obtainRequestToken()}.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step1">6.1. Obtaining an Unauthorized Request Token</a>
 */
@FunctionalInterface
public interface RequestTokenFlow {
    /**
     * Obtain the request token and return the next phase of the flow.
     * 
     * @return the {@link UserAuthorizationFlowFactory}
     */
    UserAuthorizationFlowFactory obtainRequestToken();
}
