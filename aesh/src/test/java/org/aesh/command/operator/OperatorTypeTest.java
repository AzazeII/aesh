/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aesh.command.operator;

import org.junit.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class OperatorTypeTest {

    @Test
    public void testMatches() {
        Set<OperatorType> operators = EnumSet.allOf(OperatorType.class);

        String text = "foo <bar";
        assertEquals(OperatorType.REDIRECT_IN, OperatorType.matches(operators, text, 4));
        text = "foo >bar";
        assertEquals(OperatorType.REDIRECT_OUT, OperatorType.matches(operators, text, 4));
        text = "foo >> bar";
        assertEquals(OperatorType.APPEND_OUT, OperatorType.matches(operators, text, 4));
        text = "foo 2>> bar";
        assertEquals(OperatorType.APPEND_OUT_ERROR, OperatorType.matches(operators, text, 4));
        text = "foo 2bar";
        assertEquals(OperatorType.NONE, OperatorType.matches(operators, text, 4));

        text = "foo | ";
        assertEquals(OperatorType.PIPE, OperatorType.matches(operators, text, 4));
        text = "foo |& ";
        assertEquals(OperatorType.PIPE_AND_ERROR, OperatorType.matches(operators, text, 4));

        text = "foo &";
        assertEquals(OperatorType.AMP, OperatorType.matches(operators, text, 4));
        text = "foo &&";
        assertEquals(OperatorType.AND, OperatorType.matches(operators, text, 4));
    }
}
