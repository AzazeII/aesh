/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.jreadline.console.redirection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class RedirectionParser {

    private static Pattern redirectionPattern = Pattern.compile("(2>&1)|(2>>)|(2>)|(>>)|(>)|(<)|(\\|&)|(\\|)");

    public static List<RedirectionOperation> matchAllRedirections(String buffer) {
        Matcher matcher = redirectionPattern.matcher(buffer);
        List<RedirectionOperation> reOpList = new ArrayList<RedirectionOperation>();

        while(matcher.find()) {
            if(matcher.group(1) != null) {
                reOpList.add( new RedirectionOperation(Redirection.OVERWRITE_OUT_AND_ERR,
                        buffer.substring(0, matcher.start(1))));
                buffer = buffer.substring(matcher.end(1));
                matcher = redirectionPattern.matcher(buffer);
            }
            else if(matcher.group(2) != null) {
                reOpList.add( new RedirectionOperation(Redirection.APPEND_ERR,
                        buffer.substring(0, matcher.start(2))));
                buffer = buffer.substring(matcher.end(2));
                matcher = redirectionPattern.matcher(buffer);
            }
            else if(matcher.group(3) != null) {
                reOpList.add( new RedirectionOperation(Redirection.OVERWRITE_ERR,
                        buffer.substring(0, matcher.start(3))));
                buffer = buffer.substring(matcher.end(3));
                matcher = redirectionPattern.matcher(buffer);
            }
            else if(matcher.group(4) != null) {
                reOpList.add( new RedirectionOperation(Redirection.APPEND_OUT,
                        buffer.substring(0, matcher.start(4))));
                buffer = buffer.substring(matcher.end(4));
                matcher = redirectionPattern.matcher(buffer);
            }
            else if(matcher.group(5) != null) {
                reOpList.add( new RedirectionOperation(Redirection.OVERWRITE_OUT,
                        buffer.substring(0, matcher.start(5))));
                buffer = buffer.substring(matcher.end(5));
                matcher = redirectionPattern.matcher(buffer);
            }
            else if(matcher.group(6) != null) {
                reOpList.add( new RedirectionOperation(Redirection.OVERWRITE_IN,
                        buffer.substring(0, matcher.start(6))));
                buffer = buffer.substring(matcher.end(6));
                matcher = redirectionPattern.matcher(buffer);
            }
            else if(matcher.group(7) != null) {
                reOpList.add( new RedirectionOperation(Redirection.PIPE_OUT_AND_ERR,
                        buffer.substring(0, matcher.start(7))));
                buffer = buffer.substring(matcher.end(7));
                matcher = redirectionPattern.matcher(buffer);
            }
            else if(matcher.group(8) != null) {
                reOpList.add( new RedirectionOperation(Redirection.PIPE,
                        buffer.substring(0, matcher.start(8))));
                buffer = buffer.substring(matcher.end(8));
                matcher = redirectionPattern.matcher(buffer);
            }

        }
        if(reOpList.size() == 0)
            reOpList.add(new RedirectionOperation( Redirection.NONE, buffer));
        if(buffer.trim().length() > 0)
            reOpList.add(new RedirectionOperation(Redirection.NONE, buffer));

        return reOpList;
    }
}