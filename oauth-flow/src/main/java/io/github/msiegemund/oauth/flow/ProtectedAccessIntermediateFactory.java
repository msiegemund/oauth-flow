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
 * Creates {@link ProtectedAccess} instances by consuming {@link ProtectedAccessParam}.
 * <p>
 * This factory is skipping flow phases prior to this one.
 * <p>
 * Use {@link #of(ProtectedAccessParam)} to create a new instance.
 * 
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor12">7. Accessing Protected Resources</a>
 */
@FunctionalInterface
public interface ProtectedAccessIntermediateFactory {
    /**
     * Create a new {@link ProtectedAccess} instance.
     * 
     * @return the new {@link ProtectedAccess}
     */
    ProtectedAccess protectedAccess();

    /**
     * Create a new instance of the {@link ProtectedAccessIntermediateFactory}.
     * 
     * @param pap the mandatory {@link ProtectedAccessParam}
     * @return the new {@link ProtectedAccessIntermediateFactory}
     */
    static ProtectedAccessIntermediateFactory of(ProtectedAccessParam pap) {
        return new DefaultProtectedAccessIntermediateFactory(pap);
    }
}
