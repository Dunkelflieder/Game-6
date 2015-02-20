varying vec3 fragPosition;

uniform sampler2D tex_worldPosition;
uniform sampler2D tex_worldNormal;

uniform vec3 color;
uniform vec3 position;
uniform float reach;
uniform float intensity;

uniform vec2 resolution;

float getLightIntensity(vec3 normal, vec3 lightDir){
	return max(-dot(normal, lightDir), 0.0);
}

vec4 calcLight(vec3 lightPos, vec3 lightColor, vec3 worldPosition, vec3 worldNormal, float lightReach, float lightIntensity){
	vec3 lightDir = worldPosition - lightPos;
	lightIntensity *= (1.0 - length(lightDir) / lightReach);
	lightIntensity = max(lightIntensity, 0.0);

	lightDir = normalize(lightDir);
	lightIntensity *= getLightIntensity(worldNormal, lightDir);
	lightColor *= lightIntensity;
	return vec4(lightColor, 0.0);
}

void main(){
	vec2 screenPosition = gl_FragCoord.xy / resolution;
	vec3 worldNormal = texture2D(tex_worldNormal, screenPosition).xyz;
	vec3 worldPosition = texture2D(tex_worldPosition, screenPosition).xyz;
	vec4 worldLight = vec4(0.0, 0.0, 0.0, 1.0);

	/*vec3 sunLightDirection = normalize(vec3(-1.0, -1.0, -1.0)) * 1.0;
	float bright = max(getLightIntensity(worldNormal, sunLightDirection), 0.5);
	vec3 sunColor = vec3(1.0, 1.0, 0.9);
	worldLight += vec4(sunColor * bright, 0.0);*/

//testLight
	//worldLight += calcLight(vec3(5.0, 1.0, 25.0), vec3(0.0, 0.0, 1.0), worldPosition, worldNormal, 20.0, 1.0);
	//worldLight += calcLight(vec3(20.0, 1.0, 25.0), vec3(0.0, 1.0, 0.0), worldPosition, worldNormal, 5.0, 1.0);
	//worldLight += calcLight(vec3(10.0, 1.0, 20.0), vec3(1.0, 0.0, 0.0), worldPosition, worldNormal, 10.0, 1.0);
	worldLight += calcLight(position, color, worldPosition, worldNormal, reach, intensity);
//testLight end

	gl_FragData[0] = worldLight;
}
