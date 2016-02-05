#version 110

attribute vec3 position;
attribute vec3 normal;

varying vec4 color;

uniform mat4 mvp;

//uniform vec3 lightPos;
//uniform vec3 cameraPos;
//uniform mat4 m;
//uniform mat4 v;
//uniform mat4 p;

void main(void){
	gl_Position = mvp * vec4(position, 1.0);
	
	color = vec4(position.x, position.y, position.z, 1.0);
/*	vec3 vert_camera = (v * m * vec4(position, 1.0)).xyz;
	vec3 camera = -ver_camera;

	vec3 lightPos_camera = (v*vec4(lightPos, 1)).xyz;
	vec3 lightDir_camera = lightPos_camera + camera;

	vec3 normal_camera = (v * m * vec4(normal,0)).xyz;

	vec3 norm = normalize(normal_camera);
	vec3 light = normalize(lightDir_camera);

	float cosTheta = clamp(dot(norm, light), 0, 1);	

		


*/
	//color = cosTheta * vec4(1, 1, 0, 1);//vec4(normal.x, normal.y, normal.z, 1.0);
}
