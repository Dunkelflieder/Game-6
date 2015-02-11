uniform sampler2D worldDepth;
uniform sampler2D effectsTexture;
uniform sampler2D effectsDepth;

void main(){
	float worldDepthValue = texture2D(worldDepth, gl_TexCoord[0].st);
	float effectsDepthValue = texture2D(effectsDepth, gl_TexCoord[0].st);
	
	if(worldDepthValue > effectsDepthValue) gl_FragColor = texture2D(effectsTexture, gl_TexCoord[0].st);
	else gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
}
