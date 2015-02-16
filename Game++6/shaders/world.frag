uniform bool renderFactionObject;

uniform sampler2D texture1;

varying vec4 verpos;
varying vec3 normal;

uniform sampler2D lightTex;
uniform sampler2D colorTex;
uniform sampler2D factionTex;

uniform vec4 factionColor;

void main(){
	if(renderFactionObject){
		vec4 textureLight = texture2D(lightTex, gl_TexCoord[0].st);
		vec4 textureColor = texture2D(colorTex, gl_TexCoord[0].st);
		vec4 textureFaction = texture2D(factionTex, gl_TexCoord[0].st);
		vec3 light = vec3(-1.0, -1.0, -1.0);
		float bright = max(-dot(normalize(normal), light), 0.5);

		textureColor *= vec4(textureLight.rgb * vec3(bright), 1.0);

		gl_FragColor = mix(textureColor, factionColor, textureFaction.r);
		return;
	}else{
		gl_FragColor = texture2D(lightTex, gl_TexCoord[0].st);
	}

}