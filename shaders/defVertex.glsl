#version 400

in vec3 position;
in vec4 colors_;

out vec4 colors;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    gl_Position = projection * view * model * vec4(position, 1.0);
	colors = colors_;
}