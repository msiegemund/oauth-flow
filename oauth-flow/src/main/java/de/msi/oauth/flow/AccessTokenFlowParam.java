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
import de.msi.oauth.flow.params.VerificationCode;
import de.msi.oauth.flow.user.UserRequestToken;

/**
 * All parameters, required to perform the token exchange.
 * <p>
 * It is possible to create an instance of this flows phase by using the {@link AccessTokenIntermediateFactory}.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step3">6.3. Obtaining an Access Token</a>
 */
public interface AccessTokenFlowParam extends AccessTokenFlowFactoryParam {
    /**
     * Retrieve the consumer key.
     * 
     * @return the key
     */
    ConsumerKey consumerKey();

    /**
     * Retrieve the information about weather or not the version information should be included.
     * 
     * @return <code>true</code> if the version information field should be included, otherwise <code>false</code>
     */
    boolean includeVersion();

    /**
     * Retrieve the {@link UserRequestToken} which is required to perform the token exchange.
     * 
     * @return the {@link UserRequestToken}
     */
    UserRequestToken userUnauthorizedRequestToken();

    /**
     * Retrieve the verification code.
     * 
     * @return the code
     */
    VerificationCode verificationCode();
}
