uniform sampler2D texture1;

varying vec4 verpos;
varying vec3 normal;

uniform sampler2D colorTex;
uniform sampler2D factionTex;

void main(){
	//vec4 color;
	//color = vec4(0.808, 0.488, 0.055, 1); //gold
	//color = vec4(1, 0.5, 0.5, 1); //blue

	vec4 textureColor = texture2D(colorTex, gl_TexCoord[0].st);
	vec4 textureFaction = texture2D(factionTex, gl_TexCoord[0].st);

	textureColor +=(vec4(0.2f, 0.3f, 1.0f, 1.0f) * textureFaction);

	gl_FragColor = textureColor;
	//gl_FragColor = vec4(light, light, light, 1);
	//gl_FragColor = vec4(normalize(vec3(verpos.xyz - lightPos)), 1);
}