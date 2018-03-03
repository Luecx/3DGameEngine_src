#version 330

in vec2 pass_textureCoords;
in float factor;

layout(location = 0) out vec4 out_Color;

uniform sampler2D colormap;
uniform vec3  fogColor;

void main(void){
	vec4 textureColour = texture(colormap,pass_textureCoords);
	out_Color = mix(textureColour, vec4(fogColor,1.0), factor);
}