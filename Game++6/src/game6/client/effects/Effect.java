package game6.client.effects;

public abstract class Effect {
	protected float lifeTime;
	protected final float MAX_LIFETIME;

	public Effect(float lifeTime) {
		this.lifeTime = lifeTime;
		this.MAX_LIFETIME = lifeTime;
	}

	public abstract void render();
}