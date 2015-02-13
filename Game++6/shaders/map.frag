uniform sampler2D texture1;

varying vec4 verpos;
varying vec3 normal;

void main(){

	vec4 color = texture2D(texture1, gl_TexCoord[0].st);

	gl_FragColor = color;

}