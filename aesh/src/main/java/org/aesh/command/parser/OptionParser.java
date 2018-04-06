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

package org.aesh.command.parser;

import org.aesh.command.impl.internal.ProcessedOption;
import org.aesh.parser.ParsedLineIterator;

/**
 * Each Option can define it's own parser which parse the input and add the values to ProcessedOption
 * The ParsedLineIterator is value object that's reused by other OptionParser's.
 *
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public interface OptionParser {

    /**
     * Parse from the current position in the iterator.
     * Note that the first position in the iterator contain the word with the option name.
     *
     * @param parsedLineIterator word iterator
     * @param option current option
     * @throws OptionParserException parser exception
     */
    void parse(ParsedLineIterator parsedLineIterator, ProcessedOption option) throws OptionParserException;
}
