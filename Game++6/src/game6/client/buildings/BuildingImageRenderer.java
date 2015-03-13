package game6.client.buildings;

import de.nerogar.render.*;

public class BuildingImageRenderer {

	private static RenderTarget renderTarget;
	private static ScreenProperties screenProperties;
	private static Camera guiTextureCam;

	public static Texture2D render(GameDisplay display, IClientBuilding building) {
		guiTextureCam = new Camera();
		guiTextureCam.setX(building.getSizeX() / 2f);
		guiTextureCam.setY(0.5f);
		guiTextureCam.setZ(building.getSizeY() + 2);

		renderTarget = new RenderTarget(true, new Texture2D("color", 0, 0));

		screenProperties = new ScreenProperties();
		screenProperties.setCamera(guiTextureCam);
		screenProperties.setFov(50);
		screenProperties.setScreenDimension(128, 128);
		screenProperties.setClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		screenProperties.setRenderTarget(renderTarget);

		display.setScreenProperties(screenProperties, true);

		building.render(null);

		Texture2D buildingImage = renderTarget.getTexture("color");
		renderTarget.removeTexture("color");
		renderTarget.cleanup();
		
		return buildingImage;
	}

}
