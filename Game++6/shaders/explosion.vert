varying vec4 verpos;
varying vec3 normal;
varying float burnValue;

uniform float offset;
uniform float size;

#insert perlin3D.glsl

void main(){
	verpos = gl_Vertex;
	burnValue = perlin3D2((verpos.xyz + vec3(0.0, offset, 0.0)) * 2.0);
	verpos += burnValue * vec4(gl_Normal, 0.0);
	verpos *= vec4(size, size, size, 1.0);
	
	gl_Position = gl_ModelViewProjectionMatrix * (verpos);

	normal = normalize(gl_NormalMatrix * gl_Normal);

    gl_TexCoord[0] = gl_MultiTexCoord0;
}