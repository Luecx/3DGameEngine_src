#version 330 core


in vec3 position;
in vec2 textureCoords;
in vec3 normal;

uniform mat4 transformationMatrix;
uniform mat4 proView;

uniform sampler2D displacementmap;
uniform float displacefactor;

void main(void) {

	vec3 p = position;

	if(displacefactor > 0) {
		p += normal * (texture(displacementmap,textureCoords).r - 0.5)* displacefactor;
	}
    gl_Position = proView * transformationMatrix * vec4(p,1.0);

}