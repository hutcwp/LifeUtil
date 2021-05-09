package hut.cwp.compiler.other;

import javax.lang.model.element.TypeElement;

/**
 * 通用接口
 */
public interface IProxyInfo {
    TypeElement getTypeElement();
    void generateMethods(StringBuilder builder);
    String generateJavaCode();
    String getProxyClassFullName();
}
