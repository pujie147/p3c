package com.alibaba.p3c.pmd.lang.java.rule.comment;

import com.alibaba.p3c.pmd.I18nResources;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.*;

import java.util.List;


/**
 * [Mandatory] 废弃旧方法时标明新方法的链接，如 @see 类名#方法名
 *
 * @author dell
 * @date 2021/03/05
 */
public class DeprecatedCommentedCodeRule extends AbstractAliCommentRule {

    @Override
    public Object visit(ASTCompilationUnit cUnit, Object data) {
        try {
            List<Node> annotations = cUnit.findChildNodesWithXPath("//Annotation/MarkerAnnotation/Name[@Image='Deprecated']");
            for(Node annotation:annotations){
                List<Node> actions = (List<Node>) annotation.findChildNodesWithXPath("../../../MethodDeclaration");
                for(Node action:actions) {
                    if (action instanceof ASTMethodDeclaration) {
                        Comment comment = ((ASTMethodDeclaration) action).comment();
                        if (comment instanceof FormalComment) {
                            for (JavadocElement javadocElement : comment.findChildrenOfType(JavadocElement.class)) {
                                if ("see".equals(javadocElement.tag().label)) {
                                    return super.visit(cUnit, data);
                                }
                            }
                        }
                    }
                }
                addViolationWithMessage(data, annotation,
                        I18nResources.getMessage("java.comment.DeprecatedCommentedCodeRule.rule.msg",
                                annotation.getImage()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.visit(cUnit,data);
    }
}
