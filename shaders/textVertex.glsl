#version 330 core
in vec3 position;
in vec3 colors_;
in vec2 textCoords_;

out vec3 colors;
out vec2 textCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    gl_Position = projection * view * model * vec4(position, 1.0);
    colors = colors_;
    textCoords = textCoords_;
}
