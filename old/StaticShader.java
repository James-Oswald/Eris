public class StaticShader extends ShaderProgram{
     
    private static final String VERTEX_FILE = "vertex.glsl";
    private static final String FRAGMENT_FILE = "frag.glsl";
 
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
     
     
 
}