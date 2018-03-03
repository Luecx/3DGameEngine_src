#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec3 worldSpace;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float fogFactor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];
uniform float textureStretch;

uniform vec3 randomRotation;
uniform float lightAmount;

uniform float fogGradient;
uniform float fogDensity;

void main(void) {

    float x = 3.14159 * randomRotation.x * fract(sin(dot(worldSpace.xz ,vec2(12.998,78.233))) * 54221.664);
    float y = 3.14159 * randomRotation.z * fract(sin(dot(worldSpace.xy ,vec2(15.91,78.233))) * 45692.231);
    float z = 3.14159 * randomRotation.y * fract(sin(dot(worldSpace.zy ,vec2(19.498,78.233))) * 39766.89);

    mat3 rotationMatrix = mat3(
        vec3(cos(y) * cos(z),-sin(x) * sin(y) * cos(z) + cos(x) * sin(z), cos(x) * sin(y) * cos(z) + sin(x) * sin(z)),
        vec3(-cos(y) * sin(z),sin(x) * sin(y) * sin(z) + cos(x) * cos(z), -cos(x) * sin(y) * sin(z) + sin(x) * cos(z)),
        vec3(-sin(y), -sin(x) * cos(y), cos(x) * cos(y))
    );

	vec4 worldPosition =  vec4(rotationMatrix * position + worldSpace,1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCam;
	pass_textureCoords = (textureCoords*textureStretch);

    surfaceNormal = rotationMatrix * normal;
    for(int i=0;i<lightAmount;i++){
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	float distance = length(positionRelativeToCam);
	fogFactor = 1 - exp(-pow((distance * fogDensity),fogGradient));

}