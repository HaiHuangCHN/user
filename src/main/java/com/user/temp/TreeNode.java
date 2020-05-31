package com.user.temp;

import java.util.Set;

import com.google.common.collect.Sets;

public class TreeNode {

    private int value;

    private Set<TreeNode> children;

    TreeNode(int value, TreeNode... children) {
        this.value = value;
        this.children = Sets.newHashSet(children);
    }

    public int getValue() {
        return value;
    }

    public Set<TreeNode> getChildren() {
        return children;
    }
}