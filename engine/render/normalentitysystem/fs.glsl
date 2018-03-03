#version 330

in vec2 pass_textureCoords;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in vec4 shadowCoords;
in float fogFactor;

layout(location = 0) out vec4 out_Color;

uniform float shineDamper;
uniform float reflectivity;
uniform float useLighting;
uniform float useshadowmap;
uniform float lightAmount;
uniform float discardLimit;

uniform vec3 lightColor[4];
uniform vec3 attenuation[4];
uniform vec3 renderWithVector;

uniform sampler2D normalmap;
uniform sampler2D colormap;
uniform sampler2D specularmap;
uniform sampler2D shadowmap;

uniform vec3 fogColor;

const vec2 poissonDisk[4] = vec2[](
  vec2( -0.94201624, -0.39906216 ),
  vec2( 0.94558609, -0.76890725 ),
  vec2( -0.094184101, -0.92938870 ),
  vec2( 0.34495938, 0.29387760 )
);

void main(void){

    float shadowFactor = 1.0;

    vec4 shadowPos = shadowCoords / shadowCoords.w;

    if(shadowPos.x > 0 && shadowPos.x < 1 && shadowPos.y > 0 && shadowPos.y < 1 && useshadowmap > 0.5) {
        for(int i =  0; i < 4; i++) {
            if(texture(shadowmap, shadowPos.xy + poissonDisk[i] / 700).x < shadowPos.z - 0.001 && shadowPos.z < 1){
                shadowFactor-=0.1;
            }
        }
    }


	vec4 textureColour = texture(colormap,pass_textureCoords);
    if(textureColour.a < discardLimit) discard;


	if(useLighting > 0.5) {

		vec4 normalMapValue = 2.0 * texture(normalmap, pass_textureCoords, -1.0) - 1.0;

		vec3 unitNormal = normalize(normalMapValue.rgb);
        vec3 unitVectorToCamera = normalize(toCameraVector);

        if(normalMapValue.x == -1 && normalMapValue.y == -1 && normalMapValue.z == -1 || renderWithVector.z < 0.5){
        	unitNormal = vec3(0.0,0.0,1.0);
        }

		vec3 totalDiffuse = vec3(0.0);
		vec3 totalSpecular = vec3(0.0);
		
		for(int i=0;i<lightAmount;i++){
			float distance = length(toLightVector[i]);
			float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
			vec3 unitLightVector = normalize(toLightVector[i]);	
			float nDotl = dot(unitNormal,unitLightVector);
			float brightness = max(nDotl,0.2);
			vec3 lightDirection = -unitLightVector;
			vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
			float specularFactor = dot(reflectedLightDirection , unitVectorToCamera);
			specularFactor = max(specularFactor,0.0);
			float dampedFactor = pow(specularFactor,shineDamper);
			if(attFactor == 0) {
				attFactor = 1;
			}
			totalDiffuse = totalDiffuse + (brightness * lightColor[i])/attFactor;
			totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i])/attFactor;
		}
		
		if(renderWithVector.y > 0.5) {
			totalSpecular *= texture(specularmap,pass_textureCoords).r;
			totalDiffuse *= texture(specularmap,pass_textureCoords).g;
		}
			
		totalDiffuse = max(totalDiffuse * shadowFactor, 0.1);
		if(shadowFactor < 0.9){
        		    out_Color =  vec4(totalDiffuse,1.0) * textureColour;
                }
                else{
        		    out_Color =  vec4(totalDiffuse,1.0) * textureColour + vec4(totalSpecular,0.0);
                }

	}else{
		out_Color = textureColour;
	}
    out_Color = mix(out_Color,vec4(fogColor,1.0),max(fogFactor,0));
}