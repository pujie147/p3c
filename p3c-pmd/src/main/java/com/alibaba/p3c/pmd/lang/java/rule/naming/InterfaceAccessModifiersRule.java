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
import com.alibaba.p3c.pmd.lang.java.util.ViolationUtils;
import net.sourceforge.pmd.lang.ast.GenericToken;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;

import java.util.Arrays;
import java.util.List;

/**
 * [Mandatory] 接口修饰符规则
 *
 * @author dell
 * @date 2021/03/04
 */
public class InterfaceAccessModifiersRule extends AbstractXpathRule {
    private static final String XPATH = "//ClassOrInterfaceDeclaration[@Interface='true']";

    public InterfaceAccessModifiersRule() {
        setXPath(XPATH);
    }

    private List accessModifiers = Arrays.asList("private","public","protected","static","final","abstract","synchronized","volatile","transient");

    @Override
    public void addViolation(Object data, Node node, String arg) {
        try {
            if (node instanceof ASTClassOrInterfaceDeclaration) {
                List<? extends Node> methods = node.findChildNodesWithXPath("//ClassOrInterfaceBodyDeclaration");
                for(Node method:methods){
                    ss(((ASTClassOrInterfaceBodyDeclaration) method),method,data);
                }
            } else {
                super.addViolation(data, node, arg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ss(ASTClassOrInterfaceBodyDeclaration bodyDeclaration,Node node,Object data){
        GenericToken genericToken = bodyDeclaration.jjtGetFirstToken();
        this.rule(genericToken.getImage(),node,data);
        for(;genericToken.getNext()!=null;){
            genericToken = genericToken.getNext();
            this.rule(genericToken.getImage(),node,data);
        }
    }

    public void rule(String image,Node node,Object data){
        if(accessModifiers.contains(image)){
            ViolationUtils.addViolationWithPrecisePosition(this, node, data,
                    I18nResources.getMessage("java.naming.InterfaceAccessModifiersRule.violation.msg",
                            node.getImage()));
        }
    }

}
