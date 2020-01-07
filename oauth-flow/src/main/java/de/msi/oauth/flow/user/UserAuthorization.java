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

import de.msi.oauth.flow.params.RequestToken;
import de.msi.oauth.flow.params.VerificationCode;

/**
 * Represents the user process of retrieving the service providers
 * <code>validation code</code>.
 * <p>
 * This processes implementation refers to the chapters <code>6.2.1</code>,
 * <code>6.2.2.</code> and <code>6.2.3</code> of the documentation.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step2">6.2 Obtaining an
 *      Unauthorized Request Token</a>
 */
@FunctionalInterface
public interface UserAuthorization {
    /**
     * Retrieve the users <code>verification code</code>.
     * 
     * @param requestToken the processes <code>request token</code>
     * @return the <code>verification code</code>, provided by the service
     *         provider
     */
    VerificationCode verificationCode(RequestToken requestToken);
}
