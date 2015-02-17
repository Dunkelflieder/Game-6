varying vec4 verpos;
varying vec3 normal;

uniform mat4 modelMatrix;

void main(){
	//modelMatrix = transpose(modelMatrix);
	//verpos = modelMatrix * gl_ModelViewMatrix * gl_Vertex;
	//gl_Position = modelMatrix * gl_ModelViewProjectionMatrix * gl_Vertex;

	verpos =  gl_ModelViewMatrix * modelMatrix* gl_Vertex;
	gl_Position = gl_ProjectionMatrix * verpos;

	normal = normalize(mat3(modelMatrix) * gl_Normal);

    gl_TexCoord[0] = gl_MultiTexCoord0;
}
