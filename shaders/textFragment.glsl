#version 400
in vec3 colors;
in vec2 textCoords;

out vec4 color;

uniform sampler2D ourTexture;

void main()
{
    color = texture(ourTexture, textCoords);
}
