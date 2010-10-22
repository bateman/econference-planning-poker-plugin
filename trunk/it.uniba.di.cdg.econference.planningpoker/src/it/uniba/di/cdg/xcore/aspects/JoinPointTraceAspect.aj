package it.uniba.di.cdg.xcore.aspects;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;

public aspect JoinPointTraceAspect {
	private static int callDepth;
	private static Logger logger = Logger.getLogger(PlanningPokerPlugin.ID);

	pointcut traced() : !within(JoinPointTraceAspect) && within(it.uniba.di.cdg.econference.planningpoker.*);

	before() : traced() {
		print("Before", thisJoinPoint);
		callDepth++;
	}

	after() : traced() {
		callDepth--;
		print("After", thisJoinPoint);
	}

	private void print(final String prefix, final JoinPoint point) {
		String depth = "";
		for (int i = 0; i < callDepth; i++) {
			depth += " ";
		}
		logger.log(Level.INFO, depth + prefix + ": " + point + "\n" + depth
				+ "Line: " + point.getSourceLocation());
	}
}
