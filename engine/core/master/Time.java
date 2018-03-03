package engine.core.master;

public class Time {
	
	public long timer;
	public long delta;
	public static final long SECCOND = 1000000000;
	
	public Time() {
		this.setCheckPoint();
	}
	
	public void setCheckPoint() {
		delta = System.nanoTime();
	}
	
	public void setTimer(double seccond) {
		this.timer = System.nanoTime() + (long)(seccond * SECCOND);
	}
	
	public double getPassedCheckPointTime() {
		return (double)(System.nanoTime()-delta)/(double)SECCOND;
	}
	
	public boolean timerIsUp() {
		return this.timer < System.nanoTime();
	}

	
}
