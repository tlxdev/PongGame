#version 110

attribute vec3 position;
attribute vec3 normal;

varying vec4 color;

uniform mat4 mvp;


void main(void){
	gl_Position = mvp * vec4(position, 1.0);
	//color = vec4(position.x, position.y, position.z, 1.0);
	color = vec4(normal.x, normal.y, normal.z, 1.0);
}
