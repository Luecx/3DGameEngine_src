#version 330

in vec4 pass_blend;
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;

in float fogFactor;

layout(location = 0) out vec4 out_Color;

uniform sampler2D blackMap;
uniform sampler2D redMap;
uniform sampler2D greenMap;
uniform sampler2D blueMap;
uniform sampler2D shadowMap;
uniform vec4 textureStretch;

uniform vec3 lightColor[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform float lightAmount;

uniform vec3 fogColor;

void main(void){


	vec4 rTexture = texture(redMap, pass_textureCoords * textureStretch.x) * pass_blend.r;
	vec4 gTexture = texture(greenMap, pass_textureCoords * textureStretch.y) * pass_blend.g;
	vec4 bTexture = texture(blueMap, pass_textureCoords * textureStretch.z) * pass_blend.b;
	vec4 bgTexture = texture(blackMap, pass_textureCoords * textureStretch.w) * pass_blend.a;

	vec4 textureColour = bgTexture + rTexture + gTexture + bTexture;

    vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);

	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);


	for(int i=0;i<lightAmount;i++){
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDotl = dot(unitNormal,unitLightVector);
		float brightness = max(nDotl,0.2);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
		float specularFactor = dot(reflectedLightDirection , unitVectorToCamera);
		specularFactor = max(specularFactor,0.0);
		float dampedFactor = pow(specularFactor,shineDamper);
		if(attFactor == 0) {
			attFactor = 1;
		}
		totalDiffuse = totalDiffuse + (brightness * lightColor[i])/attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i])/attFactor;
	}
	totalDiffuse = max(totalDiffuse, 0.2);
	out_Color = mix(vec4(totalDiffuse,1.0) * textureColour + vec4(totalSpecular,1.0), vec4(fogColor,1.0), fogFactor);
}