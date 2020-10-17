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

package io.github.msiegemund.oauth.flow.signature.base.uri;

import java.net.URI;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * The default implementation of {@link NormalizeUri}.
 * <p>
 * This implementation produces results in regard to the <code>OAuth</code> specification
 * <code>9.1.2. Construct Request URL</code>.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor13">9.1.2. Construct Request URL</a>
 */
public final class DefaultNormalizeUri implements NormalizeUri {
    private static final String DELIMITER = "://";
    private final URI uri;

    /**
     * Create a new instance.
     * 
     * @param uri the {@link URI} to normalize
     */
    public DefaultNormalizeUri(URI uri) {
        this.uri = Objects.requireNonNull(uri, "the uri is mandatory");
    }

    @Override
    public String normalize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.uri.getScheme()).append(DELIMITER).append(this.uri.getHost()).append(port())
                .append(this.uri.getPath());
        return sb.toString().toLowerCase();
    }

    private String port() {
        int port = this.uri.getPort();
        if (port == -1) {
            return StringUtils.EMPTY;
        }
        return NormalizedPort.lookup(this.uri.getScheme()).orElseThrow(IllegalArgumentException::new).normalize(port);
    }
}
