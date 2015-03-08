varying vec4 verpos;
varying vec3 normal;
varying float burnValue;

uniform float heat;

uniform sampler2D tex_fire;

void main(){
	gl_FragColor = vec4(texture2D(tex_fire, vec2(-burnValue, 0.5)).rgb, heat * 0.3);
	//gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
}
