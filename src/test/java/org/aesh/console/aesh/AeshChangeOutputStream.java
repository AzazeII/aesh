/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.aesh.console.aesh;

import org.aesh.command.impl.registry.AeshCommandRegistryBuilder;
import org.aesh.console.settings.Settings;
import org.aesh.console.settings.SettingsBuilder;
import org.aesh.command.registry.CommandRegistry;
import org.aesh.readline.Prompt;
import org.aesh.readline.ReadlineConsole;
import org.aesh.utils.Config;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
@Ignore
public class AeshChangeOutputStream {

    private static final String LINE_SEPARATOR = Config.getLineSeparator();

    @Test
    public void changeOutputStream() throws IOException, InterruptedException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        CommandRegistry registry = new AeshCommandRegistryBuilder().create();

        Settings settings = SettingsBuilder.builder()
                .inputStream(pipedInputStream)
                .outputStream(new PrintStream(byteArrayOutputStream))
                .setPersistExport(false)
                .persistHistory(false)
                .logging(true)
                .commandRegistry(registry)
                .build();


        ReadlineConsole console = new ReadlineConsole(settings);
        console.setPrompt(new Prompt(""));
        console.start();

        outputStream.write(("FOO" + LINE_SEPARATOR).getBytes());
        outputStream.flush();
        Thread.sleep(100);

        assertEquals("FOO"+LINE_SEPARATOR+"Command: FOO was not found."+Config.getLineSeparator(), byteArrayOutputStream.toString());

        //TODO:
        //ByteArrayOutputStream newByteArrayOutputStream = new ByteArrayOutputStream();
        //((AeshConsoleImpl) console).changeOutputStream(new PrintStream(newByteArrayOutputStream));

        Thread.sleep(100);

        outputStream.write(("Foo" + LINE_SEPARATOR).getBytes());
        outputStream.flush();
        Thread.sleep(100);

        //assertEquals("Foo"+LINE_SEPARATOR+"Command: Foo was not found."+Config.getLineSeparator(), newByteArrayOutputStream.toString());

        console.stop();
    }


}
