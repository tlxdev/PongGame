#version 110

varying vec3 normal_camera;
varying vec3 lightDir_camera;
varying vec3 normal_word;
varying vec3 eyeDir;
varying vec3 position_world;

uniform vec3 lightPos;

void main(void){

vec3 lightCol = vec3(1,1,1);
float lightPower = 1.0f;

vec3 matColor = vec3(0.05f,1.0f,0.05f);
vec3 ambientColor = vec3(0.3, 0.3, 0.3) * matColor;

float dist = length(lightPos - position_world);

vec3 n = normalize(normal_camera);
vec3 l = normalize(lightDir_camera);
float cosTheta = clamp(dot(n, l), 0, 1);
vec3 e = normalize(eyeDir);
vec3 r = reflect(-l, n);

float cosAlpha = clamp(dot(e, r), 0, 1);

vec3 color = ambientColor + (matColor * lightCol * lightPower * cosTheta / (dist*dist));


gl_FragColor = vec4(color, 1);

}
