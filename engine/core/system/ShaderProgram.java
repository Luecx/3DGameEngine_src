package engine.core.system;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

/**
 * Created by Luecx on 03.01.2017.
 */
public abstract class ShaderProgram {

    private String vertexFile;
    private String fragmentFile;
    private String geometryFile;

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private int geometryShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile) {
        this.vertexFile = vertexFile;
        this.fragmentFile = fragmentFile;
    }

    public ShaderProgram(String vertexFile, String fragmentFile, String geometryFile) {
        this.vertexFile = vertexFile;
        this.fragmentFile = fragmentFile;
        this.geometryFile = geometryFile;
    }

    public void createShader() {
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        geometryShaderID = geometryFile == null ? 0:loadShader(geometryFile, GL32.GL_GEOMETRY_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        if(geometryShaderID != 0){
            GL20.glAttachShader(programID, geometryShaderID);
        }
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
        this.start();
        connectTextureUnits();
        this.stop();
        printStartLog();
    }

    private void printStartLog() {
        String warnings = "";
        String s = this.toString();
        int start = 0, end = 2;
        int regx = 0;
        for (char c : s.toCharArray()) {
            if (c == '{') start = regx;
            if (c == '}') end = regx;
            regx++;

        }
        String h = s.substring(start + 1, end);
        for (String g : h.split(", l")) {
            String[] not = g.split("=");
            if(not.length > 1){
                for (char c : not[1].toCharArray()) {
                    if (c == '-') {
                        if(not[0].startsWith("l")){

                            warnings += "    "+ not[0] + "\n";
                        }else{

                            warnings += "    l"+ not[0] + "\n";
                        }
                        break;
                    }
                }
            }else{
                warnings = "";
                break;
            }
        }
        if (warnings.equals("")) {
            System.out.format("%-13s %-50s %-20s%n", "Starting", "<" + this.getClass().getSimpleName() + ">", "Status = COMPLETE");
        } else{
            System.err.format("%-13s %-50s %-20s%n", "Starting", "<" + this.getClass().getName() + ">", "Status = WARNINGS");
            System.err.println(warnings);
        }
    }

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniformName){
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    protected void loadFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }

    protected void loadInt(int location, int value){
        GL20.glUniform1i(location, value);
    }

    protected void loadVector(int location, Vector3f vec){
        GL20.glUniform3f(location, vec.x, vec.y, vec.z);
    }

    protected void loadVector(int location, float x, float y, float z) {
        GL20.glUniform3f(location, x, y, z);
    }

    protected void loadVector(int location, float x, float y, float z, float w) {
        GL20.glUniform4f(location, x, y, z,w);
    }

    protected void loadVector(int location, Vector4f vec){
        GL20.glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
    }

    protected void loadVector(int location, Vector2f vec){
        GL20.glUniform2f(location, vec.x, vec.y);
    }

    protected void loadBoolean(int location, boolean value){
        float toLoad = 0;
        if(value){
            toLoad = 1;
        }

        GL20.glUniform1f(location, toLoad);
    }

    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location,false, matrixBuffer);
    }


    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop(){
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);



    }

    protected abstract void connectTextureUnits();

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    private static int loadShader(String file, int type){

        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!= null){
                shaderSource.append(line).append("\n");
            }
            reader.close();
        }
        catch(Exception e){
            System.err.format("%-13s %-50s %-20s%n", "Compile" , "<"+file+">", "Status = NOT EXISTING SOURCE");
            e.printStackTrace();
            return -1;
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if(GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){ //neuere Version: glGetShaderi
            System.err.format("%-13s %-50s %-20s%n", "Compile" , "<"+file+">", "Status = INCOMPLETE");
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 10000));
        } else{
            System.out.format("%-13s %-50s %-20s%n", "Compile" , "<"+file+">", "Status = COMPLETE");
        }
        return shaderID;
    }

    public abstract String toString();

}
