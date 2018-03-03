#version 330 core

in vec2 position;

uniform vec3 worldSpace;
uniform float scale;
uniform float rotation;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform float numberOfRows;
uniform float textureIndex;

out float blend;
out vec2 texA;
out vec2 texB;

void main() {

	mat4 t = mat4(
		1,0,0,0,
		0,1,0,0,
		0,0,1,0,
		worldSpace.x,worldSpace.y,worldSpace.z,1
	);
	mat4 m = viewMatrix * t;

	m[0][0] = scale;
	m[0][1] = 0;
	m[0][2] = 0;
	m[1][0] = 0;
	m[1][1] = scale;
	m[1][2] = 0;
	m[2][0] = 0;
	m[2][1] = 0;
	m[2][2] = scale;

	mat2 rot = mat2(
	    cos(rotation), sin(rotation),
	    -sin(rotation), cos(rotation))
	;

	gl_Position = projectionMatrix * m * vec4((rot * position).xy, 1,1);
	float offset = 1 / numberOfRows;
    float xInd =  mod(int(textureIndex),numberOfRows);
	texA = vec2(
	    ((position.x + 0.5) * offset + offset * xInd),
	    ((1.0-(position.y + 0.5)) * offset + offset * int(textureIndex/numberOfRows))
	);
	blend = textureIndex - int(textureIndex);
	texB = texA + vec2(offset, 0);
    if(xInd == numberOfRows-1){
	    if(int(textureIndex) == numberOfRows * numberOfRows-1){
            texB = texA;
	    }else{
	        texB.y = texA.y + offset;
	    }
	}
}
