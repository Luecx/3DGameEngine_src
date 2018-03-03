#version 330 core



in vec3 normal;
in vec2 textureCoords;
in vec3 position;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out vec4 shadowCoords;
out float fogFactor;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition[4];
uniform vec3 renderWithVector;
uniform float textureStretch;
uniform float lightAmount;

uniform float fogGradient;
uniform float fogDensity;

uniform sampler2D displacementmap;
uniform float displacefactor;
uniform float useshadowmap;
uniform mat4 shadowmatrix;

const mat4 bias = mat4(
    0.5,0,0,0,
    0,0.5,0,0,
    0,0,0.5,0,
    0.5,0.5,0.5,1.0
);

void main(void) {


	vec3 p = position;

	if(renderWithVector.x > 0.5f) {
		p += normal * (texture(displacementmap,textureCoords).r - 0.5)* displacefactor;
	}
	
	vec4 worldPosition = transformationMatrix * vec4(p,1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCam;
	pass_textureCoords = (textureCoords*textureStretch);

      if(useshadowmap > 0.5) {
            shadowCoords = bias * shadowmatrix * worldPosition;
        }


    surfaceNormal = (transformationMatrix * vec4(normal,0.0)).xyz;
    for(int i=0;i<lightAmount;i++){
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	fogFactor = 1 - exp(-pow((length(positionRelativeToCam) * fogDensity),fogGradient));
}