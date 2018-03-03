#version 330

in vec2 textureCoords;

layout(location = 0) out vec4 out_Color;

uniform sampler2D guiTexture;
uniform float blackwhite;
uniform float ignorealpha;

uniform vec3 blendColor;
uniform float blendFactor;

void main(void){
	vec4 c = texture(guiTexture,textureCoords);
	if(blackwhite > 0.5) {
		out_Color = vec4(c.x,c.x,c.x,c.w);
	}else{
		out_Color = c;
	}
	if(ignorealpha > 0.5) {
		out_Color.w = 1.0;
	}
	out_Color = mix(out_Color, vec4(blendColor,1.0),blendFactor);
}