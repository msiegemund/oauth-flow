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

package io.github.msiegemund.oauth.flow.signature.base;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.github.msiegemund.oauth.flow.params.HttpMethod;
import io.github.msiegemund.oauth.flow.params.SignatureBaseString;
import io.github.msiegemund.oauth.flow.params.header.fields.HeaderField;
import io.github.msiegemund.oauth.flow.signature.base.uri.DefaultNormalizeUri;

/**
 * The default implementation of the {@link SignatureBaseStringBuilder}.
 *
 * @author Martin Siegemund
 */
public final class DefaultSignatureBaseStringBuilder implements SignatureBaseStringBuilder {

    private static final Log LOG = LogFactory.getLog(DefaultSignatureBaseStringBuilder.class);

    private static final String REALM_KEY = "realm";
    private static final String DELIMITER = "&";
    private final Set<Parameter> parameters = new TreeSet<>();
    private final HttpMethod httpMethod;
    private final URI uri;

    /**
     * Create a new instance.
     * 
     * @param httpMethod the {@link HttpMethod}
     * @param endpoint the {@link URI} of the endpoint
     */
    public DefaultSignatureBaseStringBuilder(HttpMethod httpMethod, URI endpoint) {
        this.httpMethod = Objects.requireNonNull(httpMethod, "the http method is mandatory");
        this.uri = Objects.requireNonNull(endpoint, "the endpoint is mandatory");
    }

    @Override
    public SignatureBaseStringBuilder add(HeaderField headerField) {
        String key = headerField.key();
        if (REALM_KEY.equals(key)) {
            LOG.trace("filtering 'realm' parameter for building signature base string");
        } else if (!this.parameters.add(new Parameter(key, headerField.value().orElse(null)))) {
            throw new IllegalArgumentException("duplicated entries detected");
        }
        return this;
    }

    @Override
    public SignatureBaseString build() {
        StringBuilder sb = new StringBuilder();
        /* add HTTP method */
        sb.append(this.httpMethod.identifier()).append(DELIMITER);
        /* add URI */
        sb.append(encode(new DefaultNormalizeUri(this.uri).normalize())).append(DELIMITER);
        /* add parameters */
        sb.append(encode(this.parameters.stream().map(Parameter::normalized).collect(Collectors.joining(DELIMITER))));
        /* return resulting string */
        return SignatureBaseString.of(sb.toString());
    }

    private static String encode(String toEncode) {
        return URLEncoder.encode(toEncode, StandardCharsets.UTF_8);
    }
}
