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
 * The entry point for performing a complete <code>OAuth 1.0a</code> authentication process.
 * <p>
 * Use {@link #of(RequestTokenFlowParam)} to create a new instance.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/">OAuth Core 1.0 Revision A</a>
 */
@FunctionalInterface
public interface OAuthFlow {
    /**
     * Create the phase for acquiring the request token.
     * 
     * @return the {@link RequestTokenFlow}
     */
    RequestTokenFlow requestTokenFlow();

    /**
     * Create a new {@link OAuthFlow} instance.
     * 
     * @param rtfp the initial parameters, required to begin the authentication flow process
     * @return a new {@link OAuthFlow} instance
     */
    static OAuthFlow of(RequestTokenFlowParam rtfp) {
        return new DefaultOauthFlow(rtfp);
    }
}
