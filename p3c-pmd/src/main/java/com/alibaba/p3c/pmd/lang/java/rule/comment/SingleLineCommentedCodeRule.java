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
package com.alibaba.p3c.pmd.lang.java.rule.comment;

import com.alibaba.p3c.pmd.I18nResources;
import net.sourceforge.pmd.lang.java.ast.*;

import java.util.List;
import java.util.regex.Pattern;


/**
 * [Recommended] 注释的双斜线与注释内容之间有且仅有一个空格
 *
 * @author keriezhang
 * @date 2017/04/14
 */
public class SingleLineCommentedCodeRule extends AbstractAliCommentRule {
    private Pattern pattern = Pattern.compile("^//[\\s][\\S].*");

    private static final String CR = "\n";

    @Override
    public Object visit(ASTCompilationUnit cUnit, Object data) {
        try {
            List<Comment> comments = cUnit.getComments();
            for (Comment comment : comments) {
                if (comment instanceof SingleLineComment) {
                    if (!pattern.matcher(comment.getImage().replaceAll(CR, "")).matches()) {
                        addViolationWithMessage(data, cUnit, I18nResources.getMessage("java.comment.SingleLineCommentedCodeRule.rule.msg"), comment.getBeginLine(), comment.getEndLine());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.visit(cUnit, data);
    }


}
