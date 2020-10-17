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

/**
 * 
 */
package io.github.msiegemund.oauth.flow.common;

import java.util.function.Supplier;

/**
 * This {@link Supplier} implementation uses a simple threadsafe caching functionality.
 *
 * @author Martin Siegemund
 * @param <T> the type of the cached and supplied value
 */
public abstract class CachedSupplier<T> implements Supplier<T> {
    private volatile boolean supplied;
    private volatile T suppliedValue;

    /**
     * Retrieve the cached value.
     * <p>
     * If the value has not been cached already, {@link #getAndCache()} gets invoked to retrieve the concrete value.
     */
    @Override
    public final T get() {
        if (!this.supplied) {
            synchronized (this) {
                if (!this.supplied) {
                    this.suppliedValue = getAndCache();
                    this.supplied = true;
                }
            }
        }
        return this.suppliedValue;
    }

    /**
     * Retrieve the concrete value which will get cached.
     * 
     * @return the value
     */
    protected abstract T getAndCache();
}
