#version 120

in vec3 position;

uniform mat4 rotx;
uniform mat4 roty;
uniform mat4 rotz;
uniform mat4 scale;
uniform mat4 translation;

void main() {
    mat4 transform = translation * rotx * roty * rotz * scale;
    gl_Position = transform * vec4(position, 1.0);
}