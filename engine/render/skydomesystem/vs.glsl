#version 330 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;
out float factor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform float radius;
uniform vec4 fogSettings;
uniform vec3 center;

void main(void) {

    mat4 m = mat4(
  		radius/2, 0, 0, 0,
   		0, radius/2, 0, 0,
   		0, 0, radius/2, 0,
   		center.x, center.y, center.z, 1
		);

    gl_Position = projectionMatrix * viewMatrix * m * vec4(position,1.0);
	pass_textureCoords = textureCoords;

	if(fogSettings.x > 0.5) {
    		factor = min(exp(-pow(((position.y- (fogSettings.w * 2 / radius)) * fogSettings.y), fogSettings.z)),1);
    	}else{
    		factor = 0;
    	}
}