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

import de.msi.oauth.flow.params.ConsumerKey;
import de.msi.oauth.flow.user.UserRequestToken;

/**
 * Mandatory parameter for creating a {@link UserAuthorizationFlow}.
 * 
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step2">6.2. Obtaining User Authorization</a>
 */
public interface UserAuthorizationFlowParam extends UserAuthorizationFlowFactoryParam {
    /**
     * Retrieve the {@link ConsumerKey}.
     * 
     * @return the {@link ConsumerKey}
     */
    ConsumerKey consumerKey();

    /**
     * Retrieve the information about weather or not, the <code>oauth_version</code> information should be included
     * within the header
     * fields.
     * 
     * @return <code>true</code> if the information should be included, otherwise <code>false</code>
     */
    boolean includeVersion();

    /**
     * Retrieve the {@link UserRequestToken}.
     * 
     * @return the {@link UserRequestToken}
     */
    UserRequestToken userUnauthorizedRequestToken();
}
