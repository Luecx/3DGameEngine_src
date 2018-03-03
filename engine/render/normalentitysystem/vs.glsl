#version 330 core


in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec3 tangent;

out vec2 pass_textureCoords;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out vec4 shadowCoords;
out float fogFactor;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPositionEyeSpace[4];
uniform vec3 renderWithVector;
uniform float textureStretch;
uniform float lightAmount;

uniform sampler2D displacementmap;
uniform float displacefactor;
uniform float useshadowmap;
uniform mat4 shadowmatrix;

uniform float fogGradient;
uniform float fogDensity;

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
    mat4 modelViewMatrix = viewMatrix * transformationMatrix;
    vec4 positionRelativeToCam = modelViewMatrix * vec4(p,1.0);
    gl_Position = projectionMatrix * positionRelativeToCam;

    if(useshadowmap > 0.5) {
        shadowCoords = bias * shadowmatrix * worldPosition;
    }

	pass_textureCoords = (textureCoords*textureStretch);

	vec3 surfaceNormal = (modelViewMatrix * vec4(normal,0.0)).xyz;

    vec3 norm = normalize(surfaceNormal);
    vec3 tang = normalize((modelViewMatrix * vec4(tangent, 0.0)).xyz);
    vec3 bitang = normalize(cross(norm, tang));

    mat3 toTangentSpace = mat3(
    	tang.x, bitang.x, norm.x,
    	tang.y, bitang.y, norm.y,
    	tang.z, bitang.z, norm.z
    );


    for(int i=0;i<lightAmount;i++){
		toLightVector[i] = toTangentSpace * (lightPositionEyeSpace[i] - positionRelativeToCam.xyz);
	}
	toCameraVector = toTangentSpace * (-positionRelativeToCam.xyz);

	fogFactor = 1 - exp(-pow((length(positionRelativeToCam) * fogDensity),fogGradient));
}