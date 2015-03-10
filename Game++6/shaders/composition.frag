uniform sampler2D tex_worldColor;
uniform sampler2D tex_worldNormal;
uniform sampler2D tex_worldPosition;
uniform sampler2D tex_worldAmbient;
uniform sampler2D tex_worldLight;

uniform sampler2D tex_effectsColor;

uniform sampler2D tex_guiColor;

uniform vec2 resolution;

/*float linearize(in float color){
	float f = 1000.0;
	float n = 0.1;
	float z = (2 * n) / (f + n - color * (f - n));
	return z;
}

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}*/

void main(){
	//world
	vec4 worldColor = texture2D(tex_worldColor, gl_TexCoord[0].st);
	vec4 worldNormal = texture2D(tex_worldNormal, gl_TexCoord[0].st);
	vec4 worldPosition = texture2D(tex_worldPosition, gl_TexCoord[0].st);
	vec4 worldAmbient = texture2D(tex_worldAmbient, gl_TexCoord[0].st);
	vec4 worldLight = texture2D(tex_worldLight, gl_TexCoord[0].st);

	//sunlight
	vec3 sunLightDirection = normalize(vec3(-1.0, -1.0, -1.0)) * 1.5;
	float bright = max(-dot(worldNormal, sunLightDirection), 0.5);
	vec3 sunColor = vec3(1.0, 1.0, 0.9);
	vec4 sunLight = vec4(sunColor * bright, 0.0);

	vec4 worldFinal = worldColor * vec4(worldAmbient.rgb * (worldLight.rgb + sunLight), 1.0);

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
	vec4 finalCombinedColor = worldFinal + effectsColor;
	gl_FragColor = mix(finalCombinedColor, guiColor, guiColor.a);
}
