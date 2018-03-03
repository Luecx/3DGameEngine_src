#version 330 core



in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec4 blend;

out vec2 pass_textureCoords;
out vec4 pass_blend;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float fogFactor;

uniform float lightAmount;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];

uniform float fogGradient;
uniform float fogDensity;

void main(void) {


	vec4 positionRelativeToCam = viewMatrix * vec4(position,1.0);
    gl_Position = projectionMatrix * positionRelativeToCam;

	pass_textureCoords = textureCoords;
    pass_blend = blend;

    surfaceNormal = normal;
    for(int i=0;i<lightAmount;i++){
		toLightVector[i] = lightPosition[i] - position;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - position;
    fogFactor = 1 - exp(-pow((length(positionRelativeToCam) * fogDensity),fogGradient));
}