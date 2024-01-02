package localizator.wrapper;

public interface IArtifactMixin {
    public String getPreName();
    public String getPostName();
    public void setPreName(String preName);
    public void setPostName(String postName);
    public String getFullLocName();
}
