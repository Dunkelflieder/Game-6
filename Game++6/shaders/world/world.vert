varying vec4 verpos;
varying vec3 normal;

uniform mat4 modelMatrix;

void main(){
	//verpos =  gl_ModelViewMatrix * modelMatrix* gl_Vertex;
	//gl_Position = gl_ProjectionMatrix * verpos;

	verpos = modelMatrix * gl_Vertex;
	gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * verpos;

	normal = normalize(mat3(modelMatrix) * gl_Normal);

    gl_TexCoord[0] = gl_MultiTexCoord0;
}
