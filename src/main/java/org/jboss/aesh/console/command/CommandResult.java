package org.jboss.aesh.console.command;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public interface CommandResult<T, F extends Formatter<T>> {

    Result getResult();

}
