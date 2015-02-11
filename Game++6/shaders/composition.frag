uniform sampler2D worldTexture;
uniform sampler2D effectsTexture;
uniform sampler2D guiTexture;

uniform vec2 resolution;

void main(){
	vec4 worldColor = texture2D(worldTexture, gl_TexCoord[0].st);
	//vec4 effectsColor = texture2D(effectsTexture, gl_TexCoord[0].st);
	vec4 guiColor = texture2D(guiTexture, gl_TexCoord[0].st);

	vec4 effectsColor = vec4(0);
	for(float i = -5; i <= 5; i += 1.0){
		for(float j = -5; j <= 5; j += 1.0){
			effectsColor += texture2D(effectsTexture, gl_TexCoord[0].st + vec2(i / resolution.x, j / resolution.y)) * (1/(abs(i)+1)) * (1/(abs(j)+1));
		}
	}

	effectsColor = (effectsColor) * 0.8;

	vec4 finalWorldColor =  worldColor + effectsColor;

	gl_FragColor = mix(finalWorldColor, guiColor, guiColor.a);
}
