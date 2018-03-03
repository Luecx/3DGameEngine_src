#version 330 core



in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec3 tangent;
in vec4 blend;

out vec2 pass_textureCoords;
out vec4 pass_blend;
out vec3 toLightVector[4];
out vec3 toCameraVector;

out float fogFactor;
out float distanceBlendFactor;

uniform float lightAmount;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];

uniform float fogGradient;
uniform float fogDensity;


void main(void) {

        mat4 modelViewMatrix = viewMatrix;
        vec4 positionRelativeToCam = modelViewMatrix * vec4(position,1.0);
        gl_Position = projectionMatrix * positionRelativeToCam;


    	pass_textureCoords = textureCoords;
        pass_blend = blend;

        float cameraDist = length(positionRelativeToCam);
    	fogFactor = exp(-pow((cameraDist * fogDensity),fogGradient));

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
                toLightVector[i] = toTangentSpace * (lightPosition[i] - positionRelativeToCam.xyz);
            }
            toCameraVector = toTangentSpace * (-positionRelativeToCam.xyz);




}