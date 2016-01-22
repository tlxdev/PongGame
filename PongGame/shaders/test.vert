#version 130

in vec3 position;

out vec4 color;

uniform mat4 mvp;


void main(void){
	gl_Position = mvp * vec4(position, 1.0);
	color = vec4(position.x, position.y, position.z, 1.0);

}
