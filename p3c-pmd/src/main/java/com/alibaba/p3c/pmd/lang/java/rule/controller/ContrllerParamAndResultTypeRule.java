//package com.alibaba.p3c.pmd.lang.java.rule.controller;
//
//import com.alibaba.p3c.pmd.I18nResources;
//import com.alibaba.p3c.pmd.lang.java.rule.comment.AbstractAliCommentRule;
//import com.alibaba.p3c.pmd.lang.java.util.ViolationUtils;
//import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
//import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
//
//import java.util.regex.Pattern;
//
///**
// * 【强制】新的接口定义参数，必须以 VO 接收参数，返回也用 VO 返回给
// * 前端。严格控制返回参数内容。
//TODO 返回统一格式 不会是vo 接收参数没有确定
// * @author DELL
// * @version 1.0
// * @date 2021/3/5 17:19
// */
//public class ContrllerParamAndResultTypeRule extends AbstractAliCommentRule{
//    private Pattern pattern = Pattern.compile(".*VO$'");
//
//    @Override
//    public Object visit(ASTAnnotation node, Object data) {
//        try {
//            node.findChildNodesWithXPath("MarkerAnnotation/Name[@Image='Controller' and //ImportDeclaration[@ImportedName='org.springframework.stereotype.Controller' or @ImportedName='org.springframework.stereotype']]").forEach(action->{
//                checkParamAndResultType(node,data);
//            });
//            node.findChildNodesWithXPath("SingleMemberAnnotation/Name[@Image='Controller' and //ImportDeclaration[@ImportedName='org.springframework.stereotype.Controller' or @ImportedName='org.springframework.stereotype']]");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return super.visit(node,data);
//    }
//
//    private void checkParamAndResultType(ASTAnnotation node,Object data){
//        try {
//            node.findChildNodesWithXPath("//ClassOrInterfaceBodyDeclaration/Annotation/*/Name[@Image='RequestMapping']").forEach(action->{
//                try {
//                    action.findChildNodesWithXPath("../../../MethodDeclaration/ResultType/Type/ReferenceType/ClassOrInterfaceType").forEach(action1->{
//                        if(!pattern.matcher(action1.getImage()).matches()){
//                            ViolationUtils.addViolationWithPrecisePosition(this, action1, data,
//                                    I18nResources.getMessage("java.controller.ContrllerParamAndResultTypeRule.rule.message",
//                                            action1.getImage()));
//                        }
//                    });
//                    action.findChildNodesWithXPath("../../../MethodDeclaration/MethodDeclarator/FormalParameters/FormalParameter/Type/ReferenceType/ClassOrInterfaceType").forEach(action1->{
//                        if(!pattern.matcher(action1.getImage()).matches()){
//                            ViolationUtils.addViolationWithPrecisePosition(this, action1, data,
//                                    I18nResources.getMessage("java.controller.ContrllerParamAndResultTypeRule.rule.message",
//                                            action1.getImage()));
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//}
