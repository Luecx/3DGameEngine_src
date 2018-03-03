#version 330 core

in vec2 texA;
in vec2 texB;
in float blend;

layout(location = 0) out vec4 out_Color;

uniform sampler2D sampler;

uniform float numberOfRows;
uniform float textureIndex;

void main() {

	out_Color = mix(texture(sampler, texA),texture(sampler, texB), blend);
}