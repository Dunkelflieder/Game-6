package game6.client.gui;

import game6.client.gui.components.GComponent;
import game6.client.gui.listener.MouseListener;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import de.nerogar.render.Camera;
import de.nerogar.util.MathHelper;

public class CameraMovementManager implements MouseListener {

	private static final float MIN_CAMERA_Y = 2;
	private static final float MAX_CAMERA_Y = 26;

	// 0 = not grabbed, 1 = grabbed for movement, 2 = grabbed for rotation
	private int grabbedType = 0;

	private int minX, minZ, maxX, maxZ;

	public interface CameraMovedListener {
		public void cameraMoved();
	}

	private List<CameraMovedListener> cameraMovedListeners = new ArrayList<>();

	private Camera camera;

	public CameraMovementManager(Camera camera) {
		this.camera = camera;
	}

	public void setBounds(int minX, int minZ, int maxX, int maxZ) {
		this.minX = minX;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxZ = maxZ;
	}
	
	@Override
	public void mouseEntered(GComponent source) {
	}

	@Override
	public void mouseLeft(GComponent source) {
	}

	@Override
	public boolean mouseClicked(GComponent source, int button) {
		// Move camera with middle mouse button
		if (button == 2) {
			if ((Mouse.getY() < Display.getHeight() * 0.35 && grabbedType != 1) || grabbedType == 2) {
				// If grabbed at lower 35% and not already grabbed for movement
				// Start rotating
				grabbedType = 2;
				return true;
			} else if (grabbedType != 2) {
				// Else if grabbed at higher 35% and not already grabbed for rotation
				// Start moving
				grabbedType = 1;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseReleased(GComponent source, int button) {
		// Middle mouse button released = stop camera movement
		if (button == 2) {
			grabbedType = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseWheel(GComponent source, int delta) {
		// Zoom in or out
		camera.setY(MathHelper.clamp(camera.getY() - 0.01f * delta, MIN_CAMERA_Y, MAX_CAMERA_Y));
		notifyCameraMovedListener();
		return true;
	}

	@Override
	public boolean mouseMoved(GComponent source, int dx, int dy) {
		if (grabbedType == 1) {
			// If grabbed for movement, translate mouse delta to world delta

			// Just move slower the farther zoomed out
			float slow = 0.01f * camera.getY();

			// yaw to radians
			float yaw = (float) (camera.getYaw());

			// Magic/Trigonometry. Do the math again if you don't trust this code.
			camera.setX(camera.getX() - (float) (slow * dx * Math.cos(yaw) + slow * dy * Math.sin(yaw)));
			camera.setZ(camera.getZ() - (float) (slow * dx * Math.sin(yaw) - slow * dy * Math.cos(yaw)));

			if (camera.getX() < minX) camera.setX(minX);
			if (camera.getZ() < minZ) camera.setZ(minZ);
			if (camera.getX() > maxX) camera.setX(maxX);
			if (camera.getZ() > maxZ) camera.setZ(maxZ);

			notifyCameraMovedListener();
			return true;
		} else if (grabbedType == 2) {
			// If grabbed for rotation, just add delta x times something to yaw.
			camera.setYaw(camera.getYaw() + dx * 0.005f);

			notifyCameraMovedListener();
			return true;
		}
		return false;
	}

	private void notifyCameraMovedListener() {
		for (CameraMovedListener listener : cameraMovedListeners) {
			listener.cameraMoved();
		}
	}

	public boolean addCameraMovedListener(CameraMovedListener listener) {
		return cameraMovedListeners.add(listener);
	}

}
