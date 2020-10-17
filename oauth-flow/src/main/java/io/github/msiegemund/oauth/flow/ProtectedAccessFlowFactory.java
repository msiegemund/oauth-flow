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
 * Creates {@link ProtectedAccess} instances by consuming {@link ProtectedAccessFlowFactoryParam}. Accessing protected
 * resources is possible after a successful <code>OAuth</code> authentication flow.
 * 
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor12">7. Accessing Protected Resources</a>
 */
@FunctionalInterface
public interface ProtectedAccessFlowFactory {
    /**
     * Create a new {@link ProtectedAccess} instance.
     * 
     * @param paffp the mandatory {@link ProtectedAccessFlowFactoryParam}
     * @return the {@link ProtectedAccess}
     */
    ProtectedAccess protectedAccess(ProtectedAccessFlowFactoryParam paffp);
}
