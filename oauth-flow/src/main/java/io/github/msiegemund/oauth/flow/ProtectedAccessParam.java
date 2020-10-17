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

import io.github.msiegemund.oauth.flow.params.ConsumerKey;
import io.github.msiegemund.oauth.flow.user.UserAccessTokenParameter;

/**
 * Provides all required parameters for creating a {@link ProtectedAccess}.
 *
 * @author Martin Siegemund
 */
public interface ProtectedAccessParam extends ProtectedAccessFlowFactoryParam {
    /**
     * Retrieve the {@link ConsumerKey}.
     * 
     * @return the key
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
     * Retrieve the user supplied {@link UserAccessTokenParameter}.
     * 
     * @return the parameter
     */
    UserAccessTokenParameter userAccessTokenParameter();
}
