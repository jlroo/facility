package edu.luc.cs439.system.facility.Chaos;

import java.sql.SQLException;

public class DatabaseDeadlockProblem implements IChaosBuilder {
	private String _method;
	private final String _name;
	private final IChaosLogger _log;
	
	public DatabaseDeadlockProblem(String name, IChaosLogger log) {
		_name = name;
		_log = log;
	}

	@Override
	public IChaosBuilder ForMethod(String name) {
		_method = name;
		return this;
	}

	@Override
	public int getProbabilityOfProblem() {return 0;}

	@Override
	public void setProbabilityOfProblem(int problemRate) { }

	@Override
	public void run() throws Exception {
		_log.Log(_name, _method, "Db connection lost");
		throw new SQLException("Transaction was chosen as a deadlock victim");
	}

}
