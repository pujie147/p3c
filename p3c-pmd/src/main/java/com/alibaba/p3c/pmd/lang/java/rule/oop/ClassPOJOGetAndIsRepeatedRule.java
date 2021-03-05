package com.alibaba.p3c.pmd.lang.java.rule.oop;

import com.alibaba.p3c.pmd.I18nResources;
import com.alibaba.p3c.pmd.lang.java.rule.AbstractPojoRule;
import com.alibaba.p3c.pmd.lang.java.util.ViolationUtils;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;

import java.util.List;


/**
 * [Mandatory] 禁止在 POJO 类中，同时存在对应属性 xxx 的 isXxx()和getXxx()方法
 *
 * @author dell
 * @date 2021/03/05
 */
public class ClassPOJOGetAndIsRepeatedRule extends AbstractPojoRule {

    @Override
    public Object visit(ASTClassOrInterfaceBodyDeclaration node, Object data) {
        try {
            List<Node> fieldNames = node.findChildNodesWithXPath("//FieldDeclaration/VariableDeclarator/VariableDeclaratorId");
            for(Node fieldName:fieldNames){
                String captureName = fieldName.getImage().substring(0, 1).toUpperCase() + fieldName.getImage().substring(1);
                List<Node> methods = node.findChildNodesWithXPath("//MethodDeclarator[@Image='is" + captureName + "'or @Image='get" + captureName + "']");
                if(methods.size()>1){
                    ViolationUtils.addViolationWithPrecisePosition(this, fieldName, data,
                            I18nResources.getMessage("java.oop.ClassPOJOGetAndIsRepeatedRule.violation.msg",
                                    fieldName.getImage()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.visit(node, data);
    }
}
