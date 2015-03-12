uniform sampler2D tex_worldColor;
uniform sampler2D tex_worldNormal;
uniform sampler2D tex_worldPosition;
uniform sampler2D tex_worldAmbient;
uniform sampler2D tex_worldLight;

uniform sampler2D tex_effectsColor;

uniform sampler2D tex_guiColor;

uniform vec2 resolution;
uniform vec3 cameraPosition;
uniform samplerCube tex_cube_sky;

/*float linearize(in float color){
	float f = 1000.0;
	float n = 0.1;
	float z = (2 * n) / (f + n - color * (f - n));
	return z;
}*/

float pow16(float f){
	f *= f;
	f *= f;
	f *= f;
	f *= f;
	return f;
}

void main(){
	//world
	vec4 worldColor = texture2D(tex_worldColor, gl_TexCoord[0].st);
	vec3 worldNormal = texture2D(tex_worldNormal, gl_TexCoord[0].st).xyz;
	vec3 worldPosition = texture2D(tex_worldPosition, gl_TexCoord[0].st).xyz;
	vec4 worldAmbient = texture2D(tex_worldAmbient, gl_TexCoord[0].st);
	vec4 worldLight = texture2D(tex_worldLight, gl_TexCoord[0].st);

	//sunlight
	vec3 sunLightDirection = normalize(vec3(-1.0, -1.0, -1.0));
	float bright = max(-dot(worldNormal, sunLightDirection), 0.5) * 1.5;
	vec3 sunColor = vec3(1.0, 1.0, 0.9);
	vec3 sunLight = sunColor * bright;

	vec4 worldFinal = worldColor * vec4(worldAmbient.x * (worldLight.rgb + sunLight), 1.0);

	//specular + reflections
	vec3 viewDirection = normalize(cameraPosition - worldPosition);
	vec3 sunReflectionDirection = reflect(sunLightDirection, worldNormal);
	vec3 reflectionDirection = reflect(viewDirection, worldNormal);

	vec4 skyColor = textureCube(tex_cube_sky, reflectionDirection);

	float specularIntensity = max(dot(viewDirection, sunReflectionDirection), 0.0);
	specularIntensity = pow16(specularIntensity) * worldAmbient.y;

	//effects
	vec4 effectsColor = vec4(0.0);
	for(float i = -5.0; i <= 5.0; i += 1.0){
		for(float j = -5.0; j <= 5.0; j += 1.0){
			vec4 effectsTexel = texture2D(tex_effectsColor, gl_TexCoord[0].st + vec2(i / resolution.x, j / resolution.y));

			effectsColor += effectsTexel * (1.0/(abs(i)+1.0)) * (1.0/(abs(j)+1.0));
		}
	}
	effectsColor = (effectsColor) * 0.5;

	//gui
	vec4 guiColor = texture2D(tex_guiColor, gl_TexCoord[0].st);

	//combine
	//vec4 finalCombinedColor = mix(worldFinal, effectsColor, effectsColor.a);
	vec4 finalCombinedColor = worldFinal + effectsColor + (specularIntensity * vec4(sunColor, 1.0));
	finalCombinedColor = mix(finalCombinedColor, skyColor, worldAmbient.y);

	gl_FragColor = mix(finalCombinedColor, guiColor, guiColor.a);
	//gl_FragColor = skyColor;

}
