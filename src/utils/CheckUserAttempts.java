package implementations;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import models.Client;

public class CheckUserAttempts {

	private int compteur;
	private long lastAttempt;
	
	private boolean isUserBlocked = false;
	private final int MAX_ATTEMPTS = 3;
	
	public CheckUserAttempts(){
		compteur = 0;
	}
	
	public boolean checkAttempts(int curCount, long curTime, long lastTime){
		if (curCount < MAX_ATTEMPTS)
			return true;
		if (curCount >= MAX_ATTEMPTS){
			if (checkTimeout(curTime, lastTime))
				return true;
			return false;
		}
		return false;
	}
	
	private boolean checkTimeout(long curTime, long lastTime) {
		long currentAttemptSeconds = TimeUnit.MILLISECONDS.toSeconds(curTime);
		long lastAttemptFail = TimeUnit.MILLISECONDS.toSeconds(lastTime);
		
		return (currentAttemptSeconds - lastAttemptFail) > 30;
	}
	
}
