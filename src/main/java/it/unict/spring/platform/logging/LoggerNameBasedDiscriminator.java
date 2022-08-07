package it.unict.spring.platform.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.Discriminator;

public class LoggerNameBasedDiscriminator implements Discriminator<ILoggingEvent> {

	private static final String LOGGER_NAME = "loggerName";
	private boolean started;
	
	@Override
	public void start() {
		started=true;
	}

	@Override
	public void stop() {
		started=false;
	}

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public String getDiscriminatingValue(ILoggingEvent e) {
		return e.getLoggerName();
	}

	@Override
	public String getKey() {
		return LOGGER_NAME;
	}

}
