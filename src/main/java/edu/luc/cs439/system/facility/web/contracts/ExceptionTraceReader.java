package edu.luc.cs439.system.facility.web.contracts;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionTraceReader {

	public static String ReadTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String stackTrace = sw.toString();
		return stackTrace;
	}
}
