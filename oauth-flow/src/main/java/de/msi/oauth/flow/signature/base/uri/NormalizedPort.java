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

package de.msi.oauth.flow.signature.base.uri;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import de.msi.oauth.flow.common.Identifiable;
import de.msi.oauth.flow.common.Lookup;
import de.msi.oauth.flow.common.LookupBuilder;

/**
 * Normalize the port information in regard to the <code>OAuth</code>
 * specification.
 *
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#anchor13">9.1.2. Construct Request
 *      URL</a>
 */
enum NormalizedPort implements Identifiable<String> {
    HTTP("http", 80),
    HTTPS("https", 443);

    private static final Lookup<String, NormalizedPort> LOOKUP = new LookupBuilder<>(NormalizedPort.class).lookup();

    private final String identifier;
    private final int port;

    NormalizedPort(String identifier, int port) {
        this.identifier = identifier;
        this.port = port;
    }

    String normalize(int portToNormalize) {
        return portToNormalize == this.port ? StringUtils.EMPTY
                : String.format(":%d", Integer.valueOf(portToNormalize));
    }

    @Override
    public String identifier() {
        return this.identifier;
    }

    static Optional<NormalizedPort> lookup(String scheme) {
        return LOOKUP.lookup(scheme.toLowerCase());
    }
}
