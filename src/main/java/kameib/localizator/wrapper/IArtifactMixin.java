package kameib.localizator.wrapper;

public interface IArtifactMixin {
    String localizator$getPreName();
    String localizator$getPostName();
    void localizator$setPreName(String preName);
    void localizator$setPostName(String postName);
    String localizator$getFullLocName();
}
