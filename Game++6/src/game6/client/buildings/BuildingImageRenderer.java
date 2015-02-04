package game6.client.buildings;

import de.nerogar.render.*;

public class BuildingImageRenderer {

	private static RenderTarget renderTarget;
	private static ScreenProperties screenProperties;
	private static Camera guiTextureCam;

	public static Texture2D render(GameDisplay display, BaseBuilding building) {
		guiTextureCam = new Camera();
		guiTextureCam.x = building.getSizeX() / 2f;
		guiTextureCam.y = 1.0f;
		guiTextureCam.z = building.getSizeY() + 0.5f;
		guiTextureCam.pitch = 30;

		renderTarget = new RenderTarget(new Texture2D("color", 0, 0));
		renderTarget.activateDepthTexture();

		screenProperties = new ScreenProperties();
		screenProperties.setCamera(guiTextureCam);
		screenProperties.setFov(90);
		screenProperties.setScreenDimension(128, 128);
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
