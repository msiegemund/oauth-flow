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

import io.github.msiegemund.oauth.flow.signature.SignatureMethod;
import io.github.msiegemund.oauth.flow.user.UserObtainAccessToken;

/**
 * The mandatory parameter for creating an {@link AccessTokenFlow} from within the flows context.
 * 
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step3">6.3. Obtaining an Access Token</a>
 */
public interface AccessTokenFlowFactoryParam {
    /**
     * Retrieve the {@link SignatureMethod}, used to create the header field signature for the token exchange phase.
     * 
     * @return the {@link SignatureMethod}
     */
    SignatureMethod signatureMethod();

    /**
     * Retrieve the user operation for obtaining the access token.
     * 
     * @return the {@link UserObtainAccessToken}
     */
    UserObtainAccessToken userObtainAccessToken();
}
