uniform sampler2D worldTexture;
uniform sampler2D worldDepth;
uniform sampler2D effectsTexture;
uniform sampler2D effectsDepth;
uniform sampler2D guiTexture;

void main(){
	vec4 worldColor = texture2D(worldTexture, gl_TexCoord[0].st);
	vec4 effectsColor = texture2D(effectsTexture, gl_TexCoord[0].st);
	vec4 guiColor = texture2D(guiTexture, gl_TexCoord[0].st);

	float worldDepthValue = texture2D(worldDepth, gl_TexCoord[0].st);
	float effectsDepthValue = texture2D(effectsDepth, gl_TexCoord[0].st);

	vec4 finalWorldColor = worldDepthValue < effectsDepthValue ? worldColor : effectsColor;

	/*vec4 finalWorldColor;
	if(worlDepth < effectsDepth){
		finalWorldColor = worldColor;
	}else{
		finalWorldColor = effectsColor;
	}*/

	gl_FragColor = mix(finalWorldColor, guiColor, guiColor.a);
}
