#version 400 core

uniform mat4 transformation;
uniform mat4 viewmatrix;
uniform mat4 projection;

in vec3 position;
in vec2 textureCoords;

void main(void) {

    vec4 worldPosition = transformation * vec4(position,1.0);
    vec4 positionRelativeToCam = viewmatrix * worldPosition;
    gl_Position = projection * positionRelativeToCam;

    gl_Position = vec4(position,1.0);
}