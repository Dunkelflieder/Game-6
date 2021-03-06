package game6.server;

public class TickTimer {

	public final int TICKRATE;
	private final double tickLength;
	private long ticks;
	private long lastMillis;
	
	public TickTimer(int tickrate) {
		this.TICKRATE = tickrate;
		this.tickLength = 1000d / TICKRATE;
	}

	/**
	 * Sets the timer's start point to now. ticks get initialized to 0
	 */
	public void start() {
		this.ticks = 0;
		this.lastMillis = now();
	}

	/**
	 * Counts the tick up and does the required delay to match the tickrate.
	 * Prints a warning if it can't keep up, but just continues normally.
	 */
	public void nextTickDelay() {
		ticks++;
		doDelay();
	}

	private long now() {
		return System.nanoTime() / 1_000_000;
	}

	private void doDelay() {

		long elapsed = now() - lastMillis;

		int waitFor = (int) (tickLength - elapsed);

		if (waitFor < 0) {
			System.out.println("Server ticking can't keep up. Running " + (-waitFor) + "ms behind. Server logic is slowed down.");
			waitFor = 0;
		}

		try {
			Thread.sleep(waitFor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		lastMillis = now();

	}

	/**
	 * @return The current tick / the tick count
	 */
	public long getTick() {
		return ticks;
	}
	
}
