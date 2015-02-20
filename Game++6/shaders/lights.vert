varying vec3 fragPosition;
varying vec3 normal;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;

	normal = normalize(gl_NormalMatrix * gl_Normal);
	fragPosition = gl_Position.xyz;

    gl_TexCoord[0] = gl_MultiTexCoord0;
}
