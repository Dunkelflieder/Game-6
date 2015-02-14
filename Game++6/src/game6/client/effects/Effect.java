package game6.client.effects;

public abstract class Effect {
	public abstract void update(float timeDelta);

	public abstract void render();
	
	public abstract boolean dead();
}