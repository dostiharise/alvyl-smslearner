package com.alvyl.smslearner.sender.matchers;

import com.alvyl.smslearner.sender.model.SenderCategory;

import java.util.regex.Pattern;

/**
 * @author dostiharise
 */
public class SenderCategoryMatcher {

    private SenderCategory senderCategory;

    private Pattern pattern;

    public SenderCategoryMatcher(SenderCategory senderCategory) {
        this.senderCategory = senderCategory;

        this.pattern = Pattern.compile(this.senderCategory.getTemplate().getPattern());

    }

    public SenderCategory getSenderCategory() {
        return senderCategory;
    }

    public void setSenderCategory(SenderCategory senderCategory) {
        this.senderCategory = senderCategory;
    }

    public boolean matches(final String content) {

        if(null == content) {
            return false;
        }

        return this.pattern.matcher(content.trim()).matches();
    }


}
