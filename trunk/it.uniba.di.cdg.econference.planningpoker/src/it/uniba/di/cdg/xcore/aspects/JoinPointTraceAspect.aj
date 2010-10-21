package it.uniba.di.cdg.xcore.aspects;

import java.util.logging.Level;
import java.util.logging.Logger;

public aspect JoinPointTraceAspect {
	private static int callDepth;
	private static Logger logger = Logger.getLogger("planningpoker");

	pointcut traced() : !within(JoinPointTraceAspect) && within(it.uniba.di.cdg.econference.planningpoker.*);

	before() : traced() {
		print("Before", thisJoinPoint);
		callDepth++;
	}

	after() : traced() {
		callDepth--;
		print("After", thisJoinPoint);
	}

	private void print(final String prefix, final Object message) {
		String depth = "";
		for (int i = 0; i < callDepth; i++) {
			depth += " ";
		}
		logger.log(Level.INFO , depth + prefix + ": " + message);
	}
}
