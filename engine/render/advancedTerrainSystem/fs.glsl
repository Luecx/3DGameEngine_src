#version 330

in vec4 pass_blend;
in vec2 pass_textureCoords;
in vec3 toLightVector[4];
in vec3 toCameraVector;

in float fogFactor;

layout(location = 0) out vec4 out_Color;

uniform sampler2D c1;
uniform sampler2D c2;
uniform sampler2D c3;
uniform sampler2D c4;

uniform sampler2D n1;
uniform sampler2D n2;
uniform sampler2D n3;
uniform sampler2D n4;

uniform sampler2D overlay;

uniform sampler2D shadowMap;
uniform vec4 textureStretch;

uniform float lightAmount;
uniform vec3 lightColor[4];
uniform vec3 attenuation[4];

uniform vec4 shineDamper;
uniform vec4 reflectivity;

uniform vec3 fogColor;

uniform float distA = 120;
uniform float distB = 300;
uniform float overlayA = 500;
uniform float overlayB = 800;

vec4 samplColorTex(vec2 coords) {
    return
       texture(c1, coords * textureStretch.x) * pass_blend.x +
       texture(c2, coords * textureStretch.y) * pass_blend.y +
       texture(c3, coords * textureStretch.z) * pass_blend.z +
       texture(c4, coords * textureStretch.w) * pass_blend.w;
}

vec4 samplColorNorm(vec2 coords) {
    return
       texture(n1, coords * textureStretch.x) * pass_blend.x +
              texture(n2, coords * textureStretch.y) * pass_blend.y +
              texture(n3, coords * textureStretch.z) * pass_blend.z +
              texture(n4, coords * textureStretch.w) * pass_blend.w;
}


void main(void){

   float shineDamperV = shineDamper.x * pass_blend.r + shineDamper.y * pass_blend.g + shineDamper.z * pass_blend.b + shineDamper.w * pass_blend.w;
   float reflectivityV = reflectivity.x * pass_blend.r + reflectivity.y * pass_blend.g + reflectivity.z * pass_blend.b + reflectivity.w * pass_blend.w;

   vec3 totalDiffuse = vec3(0.0);
   vec3 totalSpecular = vec3(0.0);

   vec3 unitVectorToCamera = normalize(toCameraVector);

   vec4 texColor;
       float depth = gl_FragCoord.z / gl_FragCoord.w;
       vec3 unitNormal = vec3(0,0,1);
        if(depth < overlayB) {
            texColor  = samplColorTex(pass_textureCoords);
            if(depth > overlayA) {
                texColor = mix(texColor, texture(overlay, pass_textureCoords), (depth - overlayA) / (overlayB - overlayA));
            }
        }else{
            texColor = texture(overlay, pass_textureCoords);
        }
       if(depth < distB) {
           unitNormal = normalize((samplColorNorm(pass_textureCoords) * 2 - 1).rgb);
           if(depth > distA) {
               float v = (depth - distA) / (distB - distA);
               unitNormal = normalize(v * vec3(0,0,1) + (1 - v) * unitNormal);

           }
       }


       for(int i=0;i<lightAmount;i++){
           float distance = length(toLightVector[i]);
           float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
           vec3 unitLightVector = normalize(toLightVector[i]);
           float nDot1 = dot(unitNormal,unitLightVector);
           float brightness = min(0.8, max(nDot1,0.1));

           vec3 lightDirection = -unitLightVector;
           vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
           float specularFactor = dot(reflectedLightDirection , unitVectorToCamera);
           specularFactor = max(specularFactor,0.0);
           float dampedFactor = pow(specularFactor,shineDamperV);
           if(attFactor == 0) {
            	attFactor = 1;
           }
           totalDiffuse = totalDiffuse + (brightness * lightColor[i])/attFactor;
           totalSpecular = totalSpecular + (dampedFactor * reflectivityV * lightColor[i])/attFactor;
       }
       out_Color =  mix(
        vec4(fogColor,1.0),
       (vec4(totalDiffuse,1.0) * texColor + vec4(totalSpecular.xyz, 1.0)), fogFactor);
}