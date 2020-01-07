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

package de.msi.oauth.flow;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import de.msi.oauth.flow.params.ConsumerKey;
import de.msi.oauth.flow.params.header.fields.HeaderFields;
import de.msi.oauth.flow.signature.SignatureMethod;
import de.msi.oauth.flow.user.UserObtainRequestToken;

/**
 * The parameter, required for the {@link RequestTokenFlow}.
 * <p>
 * Use {@link RequestTokenFlowParamBuilder} to build an instance of {@link RequestTokenFlowParam}.
 * 
 * @author Martin Siegemund
 * @see <a href="https://oauth.net/core/1.0a/#auth_step1">6.1. Obtaining an Unauthorized Request Token</a>
 */
public interface RequestTokenFlowParam {
    /**
     * Retrieve the <code>consumer_key</code>.
     * 
     * @return the key
     */
    ConsumerKey consumerKey();

    /**
     * Retrieve the {@link SignatureMethod}, used to sign the header fields for acquiring the request token.
     * 
     * @return the {@link SignatureMethod}
     */
    SignatureMethod signatureMethod();

    /**
     * Retrieve the information about weather or not, the <code>oauth_version</code> information should be included
     * within the header
     * fields.
     * 
     * @return <code>true</code> if the information should be included, otherwise <code>false</code>
     */
    boolean includeVersion();

    /**
     * An absolute URL to which the Service Provider will redirect the User back when the Obtaining User Authorization
     * (Obtaining User Authorization) step is completed.
     * 
     * @return the {@link URI} or {@link Optional#empty()}
     */
    Optional<URI> callback();

    /**
     * Any additional parameters, as defined by the Service Provider.
     * 
     * @return the {@link HeaderFields}
     */
    HeaderFields additionalParams();

    /**
     * The user operation which is capable of retrieving the request token.
     * 
     * @return the {@link UserObtainRequestToken}
     */
    UserObtainRequestToken userObtainRequestToken();

    /**
     * Creates {@link RequestTokenFlowParam} instances.
     * 
     * @author Martin Siegemund
     */
    public static final class RequestTokenFlowParamBuilder {
        private final ConsumerKey consumerKey;
        private final SignatureMethod signatureMethod;
        private final AtomicBoolean includeVersion = new AtomicBoolean();
        private final AtomicReference<URI> callback = new AtomicReference<>();
        private final AtomicReference<HeaderFields> additionalParams = new AtomicReference<>(HeaderFields.empty());
        private final UserObtainRequestToken userObtainUnauthorizedRequestToken;

        /**
         * Create a new builder instance.
         * 
         * @param consumerKey the consumer key
         * @param signatureMethod the {@link SignatureMethod}
         * @param userObtainRequestToken the {@link UserObtainRequestToken} operation
         */
        public RequestTokenFlowParamBuilder(ConsumerKey consumerKey, SignatureMethod signatureMethod,
                UserObtainRequestToken userObtainRequestToken) {
            this.consumerKey = consumerKey;
            this.signatureMethod = signatureMethod;
            this.userObtainUnauthorizedRequestToken = userObtainRequestToken;
        }

        /**
         * Decide weather or not the version information should be included.
         * 
         * @param value <code>true</code> if the information should be included, otherwise <code>false</code>
         * @return the builder
         */
        public RequestTokenFlowParamBuilder includeVersion(boolean value) {
            this.includeVersion.set(value);
            return this;
        }

        /**
         * Set the callback {@link URI}.
         * 
         * @param value the callback
         * @return the builder
         */
        public RequestTokenFlowParamBuilder callback(URI value) {
            this.callback.set(value);
            return this;
        }

        /**
         * Add some additional header fields (as defined by the service provider).
         * 
         * @param value additional {@link HeaderFields}
         * @return the builder
         */
        public RequestTokenFlowParamBuilder additionalParams(HeaderFields value) {
            this.additionalParams.set(value);
            return this;
        }

        /**
         * Build a new {@link RequestTokenFlowParam} providing the previously added values.
         * 
         * @return the {@link RequestTokenFlowParam}
         */
        public RequestTokenFlowParam build() {
            return new DefaultRequestTokenFlowParam(this.consumerKey, this.signatureMethod, this.includeVersion.get(),
                    this.callback.get(), this.additionalParams.get(), this.userObtainUnauthorizedRequestToken);
        }
    }
}
