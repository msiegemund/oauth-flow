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

package de.msi.oauth.flow.user;

import de.msi.oauth.flow.params.header.fields.HeaderFields;

/**
 * Represents the user operation for obtaining the <code>access token</code>.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step3">6.3. Obtaining an
 *      Access Token</a>
 */
@FunctionalInterface
public interface UserObtainAccessToken {
    /**
     * Obtain the access token and return the processes result.
     * 
     * @param fields the required header fields for obtaining the
     *        <code>access token</code>
     * @return the {@link UserAccessTokenParameter} contain the relevant service
     *         providers response parameters
     */
    UserAccessTokenParameter obtainAccessToken(HeaderFields fields);
}
