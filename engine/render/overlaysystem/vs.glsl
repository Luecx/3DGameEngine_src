#version 330

in vec2 position;
in vec2 texCoords;

out vec2 textureCoords;

uniform mat4 transformationMatrix;
uniform float useTexCoords;

void main(void){
	gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
	if(useTexCoords > 0.5) {
	    textureCoords = texCoords;
	}else{
	    textureCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);
	}
}