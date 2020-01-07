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

package de.msi.oauth.flow.params;

import java.util.Objects;

import de.msi.oauth.flow.common.Identifiable;

/**
 * Represents the supported <code>HTTP</code> methods.
 *
 * @author Martin Siegemund
 */
public enum HttpMethod implements Identifiable<String> {
    /**
     * Represents the <code>HTTP</code> method <code>GET</code>.
     */
    GET("GET"),
    /**
     * Represents the <code>HTTP</code> method <code>POST</code>.
     */
    POST("POST");

    private final String identifier;

    HttpMethod(String identifier) {
        this.identifier = Objects.requireNonNull(identifier);
    }

    @Override
    public String identifier() {
        return this.identifier;
    }
}
