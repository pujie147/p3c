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
import com.alibaba.p3c.pmd.lang.java.util.ViolationUtils;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotationTypeDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBody;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;

import java.util.List;

/**
 * [Mandatory] spring bean的命名规则
 *
 * @author changle.lq
 * @date 2017/04/16
 */
public class SpringBeanNamingRule extends AbstractAliRule {

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        try {
            List<Node> services = node.findChildNodesWithXPath("../Annotation/SingleMemberAnnotation/Name[@Image='Service' and //ImportDeclaration[@ImportedName='org.springframework.stereotype.Service' or @ImportedName='org.springframework.stereotype']]");
            for(Node service :services) {
                difClassNameSpringName(service, node, data);
            }

            List<Node> components = node.findChildNodesWithXPath("../Annotation/SingleMemberAnnotation/Name[@Image='Component' and //ImportDeclaration[@ImportedName='org.springframework.stereotype.Component' or @ImportedName='org.springframework.stereotype']]");
            for(Node component :components) {
                difClassNameSpringName(component, node, data);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return super.visit(node,data);
    }

    private void difClassNameSpringName(Node service,ASTClassOrInterfaceDeclaration node,Object data) throws Exception{
        String serviceName = node.getImage().substring(0, 1).toLowerCase() + node.getImage().substring(1);
        String beanName = service.findChildNodesWithXPath("../MemberValue/PrimaryExpression/PrimaryPrefix/Literal").get(0).getImage().replaceAll("\"", "");
        if(!serviceName.equals(beanName)){
            ViolationUtils.addViolationWithPrecisePosition(this, node, data,
                    I18nResources.getMessage("java.naming.SpringBeanNamingRule.violation.msg",
                            node.getImage()));
        }
    }
}
