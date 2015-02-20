uniform bool renderFactionObject;

uniform sampler2D texture1;

varying vec4 verpos;
varying vec3 normal;

uniform sampler2D colorTex;
uniform sampler2D ambientTex;
uniform sampler2D factionTex;

uniform vec4 factionColor;

void main(){
	vec4 out_color;
	vec4 out_normal;
	vec4 out_position;
	vec4 out_ambient;

	if(renderFactionObject){
		vec4 textureAmbient = texture2D(ambientTex, gl_TexCoord[0].st);
		vec4 textureColor = texture2D(colorTex, gl_TexCoord[0].st);
		vec4 textureFaction = texture2D(factionTex, gl_TexCoord[0].st);

		out_color = mix(textureColor, factionColor, textureFaction.r);
		out_ambient = textureAmbient;
	}else{
		out_color = texture2D(colorTex, gl_TexCoord[0].st);
		out_ambient = vec4(1.0);
	}

	out_normal = vec4(normalize(normal), 1.0);
	out_position = verpos;

	gl_FragData[0] = out_color;
	gl_FragData[1] = out_normal;
	gl_FragData[2] = out_position;
	gl_FragData[3] = out_ambient;
}
