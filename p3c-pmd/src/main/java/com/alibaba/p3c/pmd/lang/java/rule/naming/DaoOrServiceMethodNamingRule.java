/*
 * Copyright 1999-2017 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.p3c.pmd.lang.java.rule.naming;

import com.alibaba.p3c.pmd.I18nResources;
import com.alibaba.p3c.pmd.lang.AbstractXpathRule;
import com.alibaba.p3c.pmd.lang.java.rule.AbstractAliRule;
import com.alibaba.p3c.pmd.lang.java.rule.comment.AbstractAliCommentRule;
import com.alibaba.p3c.pmd.lang.java.util.ViolationUtils;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;

import java.util.List;
import java.util.regex.Pattern;

/**
 * [Mandatory] DAO or Service Interface naming rule
 * @author dell
 * @date 2021/02/27
 */
public class DaoOrServiceMethodNamingRule extends AbstractAliCommentRule {

    private Pattern pattern1 = Pattern.compile("^(get|find|save|delete|update).*$");

    private Pattern pattern2 = Pattern.compile("^(get.+Count|find.+List|get.+By.+|find.+ListBy.+)$");

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        try {
            List<Node> interFaceNodes = node.findChildNodesWithXPath("//ClassOrInterfaceDeclaration [@Abstract='false' and @Interface='true'] [ (matches(@Image,'^.*(Service|DAO)'))]");
            if(interFaceNodes!=null && interFaceNodes.size()>0){
                List<? extends Node> methodNodes = node.findChildNodesWithXPath("./ClassOrInterfaceBody/ClassOrInterfaceBodyDeclaration/MethodDeclaration");
                methodNodes.forEach(method -> {
                    if (!(pattern1.matcher(((ASTMethodDeclaration) method).getQualifiedName().getOperation()).matches())) {
                        if (!(pattern2.matcher(((ASTMethodDeclaration) method).getQualifiedName().getOperation()).matches())) {
                            ViolationUtils.addViolationWithPrecisePosition(this, method, data,
                                    I18nResources.getMessage("java.naming.DaoOrServiceMethodNamingRule.violation.msg",
                                            method.getImage()));
                            return;
                        }
                        ViolationUtils.addViolationWithPrecisePosition(this, method, data,
                                I18nResources.getMessage("java.naming.DaoOrServiceMethodNamingRule.violation.msg",
                                        method.getImage()));
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.visit(node, data);
    }
}
