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
 * Creates {@link AccessTokenFlow} instances by consuming the {@link AccessTokenFlowFactoryParam}.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step3">6.3. Obtaining an Access Token</a>
 */
@FunctionalInterface
public interface AccessTokenFlowFactory {
    /**
     * Create a new {@link AccessTokenFlow} instance.
     * 
     * @param atfp the mandatory {@link AccessTokenFlowParam}
     * @return the {@link AccessTokenFlow}
     */
    AccessTokenFlow accessTokenFlow(AccessTokenFlowFactoryParam atfp);
}
