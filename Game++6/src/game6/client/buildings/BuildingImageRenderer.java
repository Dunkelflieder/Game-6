package game6.client.buildings;

import game6.core.buildings.CoreBuilding;
import de.nerogar.render.Camera;
import de.nerogar.render.GameDisplay;
import de.nerogar.render.RenderTarget;
import de.nerogar.render.ScreenProperties;
import de.nerogar.render.Texture2D;

public class BuildingImageRenderer {

	private static RenderTarget renderTarget;
	private static ScreenProperties screenProperties;
	private static Camera guiTextureCam;

	public static Texture2D render(GameDisplay display, CoreBuilding building) {
		guiTextureCam = new Camera();
		guiTextureCam.x = building.getSizeX() / 2f;
		guiTextureCam.y = 1.0f;
		guiTextureCam.z = building.getSizeY() + 0.5f;
		guiTextureCam.pitch = 30;

		renderTarget = new RenderTarget(true, new Texture2D("color", 0, 0));

		screenProperties = new ScreenProperties();
		screenProperties.setCamera(guiTextureCam);
		screenProperties.setFov(90);
		screenProperties.setScreenDimension(128, 128);
		screenProperties.setClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		screenProperties.setRenderTarget(renderTarget);

		display.setScreenProperties(screenProperties, true);

		building.render();

		System.out.println("abc: " + building.getPosX());

		Texture2D buildingImage = renderTarget.getTexture("color");
		renderTarget.removeTexture("color");
		renderTarget.cleanup();

		return buildingImage;
	}

}
