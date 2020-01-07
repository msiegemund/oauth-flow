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
 * This represents an user operation which goal is to obtain an unauthorized
 * request token.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step1">Obtaining an
 *      Unauthorized Request Token</a>
 */
@FunctionalInterface
public interface UserObtainRequestToken {
    /**
     * Obtain an unauthorized request token.
     * 
     * @param headerParams the generated {@link HeaderFields} which MUST be
     *        sent to the service provider
     * @return the {@link UserRequestToken} which represents the
     *         result of this operation
     */
    UserRequestToken requestToken(HeaderFields headerParams);
}
