package game6.server;

public class TickTimer {

	public final int TICKRATE;
	private long ticks;
	private long lastMillis;

	public TickTimer(int tickrate) {
		this.TICKRATE = tickrate;
	}

	/**
	 * Sets the timer's start point to now. ticks get initialized to 0
	 */
	public void start() {
		this.ticks = 0;
		this.lastMillis = System.currentTimeMillis();
	}

	/**
	 * Counts the tick up and does the required delay to match the tickrate.
	 * Prints a warning if it can't keep up, but just continues normally.
	 */
	public void nextTickDelay() {
		ticks++;
		doDelay();
	}

	private void doDelay() {

		long elapsed = System.currentTimeMillis() - lastMillis;

		double tickLength = 1000d / TICKRATE;
		int waitFor = (int) (tickLength - elapsed);

		System.out.println("Waiting " + waitFor + " ms");

		if (waitFor < 0) {
			System.out.println("Can't keep up! Did the system time change, or is the server overloaded? Running " + (-waitFor) + "ms behind. Server logic is slowed down.");
			waitFor = 0;
		}
		try {
			Thread.sleep(waitFor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		lastMillis = System.currentTimeMillis();

	}

	/**
	 * @return The current tick / the tick count
	 */
	public long getTick() {
		return ticks;
	}

}
