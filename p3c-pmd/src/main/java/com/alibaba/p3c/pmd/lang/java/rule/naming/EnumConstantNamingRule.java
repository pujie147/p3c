package com.alibaba.p3c.pmd.lang.java.rule.naming;

import com.alibaba.p3c.pmd.I18nResources;
import com.alibaba.p3c.pmd.lang.java.rule.comment.AbstractAliCommentRule;
import com.alibaba.p3c.pmd.lang.java.util.ViolationUtils;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTEnumConstant;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 枚举 常量命名规则
 *
 * @author DELL
 * @version 1.0
 * @date 2021/3/1 13:57
 */
public class EnumConstantNamingRule extends AbstractAliCommentRule {
    private Pattern pattern1 = Pattern.compile("[A-Z0-9_]+");

    @Override
    public Object visit(ASTCompilationUnit cUnit, Object data) {
        List<ASTEnumConstant> contantDecl = cUnit.findDescendantsOfType(ASTEnumConstant.class);
        for(ASTEnumConstant astEnumConstant:contantDecl){
            if(!pattern1.matcher(astEnumConstant.getImage()).matches()){
                ViolationUtils.addViolationWithPrecisePosition(this, astEnumConstant, data,
                        I18nResources.getMessage("java.naming.EnumConstantNamingRule.rule.msg",
                                astEnumConstant.getImage()));
            }
        }
        return super.visit(cUnit, data);
    }
}
