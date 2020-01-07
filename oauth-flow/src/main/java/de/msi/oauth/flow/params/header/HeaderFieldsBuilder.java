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

package de.msi.oauth.flow.params.header;

import java.net.URI;

import de.msi.oauth.flow.params.ConsumerKey;
import de.msi.oauth.flow.params.Token;
import de.msi.oauth.flow.params.TokenSecret;
import de.msi.oauth.flow.params.VerificationCode;
import de.msi.oauth.flow.params.header.fields.HeaderFields;
import de.msi.oauth.flow.signature.SignatureMethod;

/**
 * Helps building a {@link HeaderFields} instance in regard to the fields which
 * have been added.
 *
 * @author Martin Siegemund
 */
public interface HeaderFieldsBuilder {
    /**
     * Add the <code>oauth_consumer_key</code>.
     * 
     * @param consumerKey the <code>oauth_consumer_key</code>
     * @return the builder
     */
    HeaderFieldsBuilder consumerKey(ConsumerKey consumerKey);

    /**
     * Invoke this method if the <code>oauth_version</code> information
     * (<code>1.0</code>) should be added to the header fields.
     * 
     * @return the builder
     */
    HeaderFieldsBuilder versionInformation();

    /**
     * Add the <code>oauth_token</code>.
     * 
     * @param token the <code>oauth_token</code>
     * @return the builder
     */
    HeaderFieldsBuilder token(Token token);

    /**
     * Add the <code>oauth_verifier</code>.
     * 
     * @param verifier the <code>oauth_verifier</code>
     * @return the builder
     */
    HeaderFieldsBuilder verifier(VerificationCode verifier);

    /**
     * Add the <code>oauth_token_secret</code>.
     * <p>
     * This is required for certain signing methods.
     * 
     * @param tokenSecret the <code>oauth_token_secret</code>
     * @return the builder
     */
    HeaderFieldsBuilder tokenSecret(TokenSecret tokenSecret);

    /**
     * Add additional (service provider defined) fields.
     * 
     * @param additionalParams the additional fields
     * @return the builder
     */
    HeaderFieldsBuilder additionalParameter(HeaderFields additionalParams);

    /**
     * Build the {@link HeaderFields} instance by using the given
     * {@link SignatureMethod}.
     * 
     * @param signatureMethod the desired {@link SignatureMethod}
     * @return the resulting {@link HeaderFields}
     */
    HeaderFields build(SignatureMethod signatureMethod);

    /**
     * Create a new {@link HeaderFieldsBuilder} which provides an <code>oauth_callback</code>.
     * 
     * @param callback the callback address
     * @return the {@link HeaderFieldsBuilder}
     */
    static HeaderFieldsBuilder withCallback(URI callback) {
        return new DefaultHeaderFieldsBuilder(new OauthHeaderFieldsValues.DefaultOauthHeaderFieldsValues(),
                new CallbackParameter.WithCallback(callback));
    }

    /**
     * Create a new {@link HeaderFieldsBuilder} which provides an <code>oauth_callback</code> field with the value
     * <code>oob</code> (out-of-band).
     * 
     * @return the {@link HeaderFieldsBuilder}
     */
    static HeaderFieldsBuilder withOutOfBandsCallback() {
        return new DefaultHeaderFieldsBuilder(new OauthHeaderFieldsValues.DefaultOauthHeaderFieldsValues(),
                new CallbackParameter.OutOfBandCallback());
    }

    /**
     * Create a new {@link HeaderFieldsBuilder} without providing an <code>oauth_callback</code> field.
     * 
     * @return the {@link HeaderFieldsBuilder}
     */
    static HeaderFieldsBuilder withoutCallback() {
        return new DefaultHeaderFieldsBuilder(new OauthHeaderFieldsValues.DefaultOauthHeaderFieldsValues(),
                new CallbackParameter.NoCallback());
    }
}
