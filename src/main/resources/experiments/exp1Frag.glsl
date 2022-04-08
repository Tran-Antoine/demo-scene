#version 120

in vec3 color;
uniform float time;

/*void main()
{
    vec2 uv = vec2(color.xy);
    float x = abs(uv.y + 0.3 * exp(-abs(uv.x)) * cos(6 * uv.x + 10*time));
    if (x > 0.1 * min(1, (1 - abs(uv.x)) * 2)) discard;
    gl_FragColor = vec4(color * sin(2*time) + 0.2, 1.0);
}*/

void main()
{
    vec2 uv = vec2(color.xy);
    float r = sqrt(uv.x * uv.x + uv.y * uv.y);
    float theta = atan(uv.y, uv.x);
    if (abs(r - abs(sin(time)) * 0.8) > 0.1 * min(1, 0.2 + abs(sin(time)) * sin(20 * theta * sin(theta + time) + 10*time))) discard;
    gl_FragColor = vec4(color * sin(2*time) + 0.2, 1.0);
}
