#version 110

attribute vec3 position;
attribute vec3 normal;

varying vec4 color;
varying vec3 normal_camera;
varying vec3 lightDir_camera;
varying vec3 normalVarying;
varying vec3 eyeDir;
varying vec3 position_world;
varying vec3 normal_world;

uniform mat4 mvp;

uniform vec3 lightPos;
uniform vec3 cameraPos;
uniform mat4 m;
uniform mat4 v;
uniform mat4 p;

//blinn-phong shaderi opas osoitteesta http://www.opengl-tutorial.org/beginners-tutorials/tutorial-8-basic-shading/


void main(void){
	gl_Position = mvp * vec4(position, 1.0);
	position_world = (m * vec4(position, 1.0)).xyz;
	vec3 pos_camera = (v * m * vec4(position, 1.0)).xyz;
	eyeDir =  cameraPos - pos_camera;
	vec3 lightPos_camera = (v * vec4(lightPos, 1)).xyz;
	lightDir_camera = lightPos_camera - pos_camera;
	normal_camera = (v * m * vec4(normal, 0)).xyz;
}
