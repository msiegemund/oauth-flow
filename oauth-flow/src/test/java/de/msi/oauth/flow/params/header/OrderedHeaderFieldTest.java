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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.msi.oauth.flow.params.header.fields.HeaderField;

final class OrderedHeaderFieldTest {

    @Test
    void testOrderingSuccessful() {
        OrderedHeaderFields orderedHeaderFields = new OrderedHeaderFields();
        orderedHeaderFields.add("KEY_g", "VAL_g");
        orderedHeaderFields.add("KEY_j", "VAL_j");
        orderedHeaderFields.add("KEY_e", "VAL_e");
        orderedHeaderFields.add("KEY_a", "VAL_a");
        orderedHeaderFields.add("KEY_c", "VAL_c");
        orderedHeaderFields.add("KEY_a", "VAL_b");
        testSuccessfulOrdering(orderedHeaderFields);
    }

    @Test
    void testOrderingDuplicates() {
        OrderedHeaderFields orderedHeaderFields = new OrderedHeaderFields();
        orderedHeaderFields.add("KEY_g", "VAL_g");
        orderedHeaderFields.add("KEY_j", "VAL_j");
        orderedHeaderFields.add("KEY_e", "VAL_e");
        Assertions.assertThrows(IllegalArgumentException.class, () -> orderedHeaderFields.add("KEY_g", "VAL_g"),
                "duplicated entry not successfully detected");
    }

    private static void testSuccessfulOrdering(OrderedHeaderFields orderedHeaderFields) {
        HeaderField successor = null;
        for (HeaderField headerField : orderedHeaderFields.fields()) {
            if (successor != null) {
                if (!Comparable.class.isAssignableFrom(successor.getClass())) {
                    Assertions.fail("header field is not implementing the Comparable interface");
                }
                @SuppressWarnings("unchecked")
                int compareResult = Comparable.class.cast(headerField).compareTo(successor);
                if (compareResult == 0) {
                    Assertions.fail("duplicated entry detected");
                } else if (compareResult < 0) {
                    Assertions.fail("wrong ordering detected");
                }
            }
            successor = headerField;
        }
    }
}
