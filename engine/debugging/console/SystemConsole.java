package engine.debugging.console;

import java.io.*;

/**
 * Created by Luecx on 03.02.2017.
 */
public class SystemConsole extends PrintStream{


    public SystemConsole(OutputStream out) {
        super(out);
    }

    public SystemConsole(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public SystemConsole(OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
        super(out, autoFlush, encoding);
    }

    public SystemConsole(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public SystemConsole(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public SystemConsole(File file) throws FileNotFoundException {
        super(file);
    }

    public SystemConsole(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    public void setToJavaOutputStream() {
        System.setOut(this);
    }

    public void setToJavaErrorStream() {
        System.setErr(this);
    }
}
