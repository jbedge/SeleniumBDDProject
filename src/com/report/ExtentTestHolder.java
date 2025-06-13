//package com.report;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.aventstack.extentreports.ExtentTest;
//
//class ExtentTestHolder {
//    private final ExtentTestHolder parent;
//    private final List<ExtentTestHolder> children;
//    private final ExtentTest extentTest;
//    private final boolean isTestNode;
//
//    public ExtentTestHolder(
//        ExtentTest extentTest,
//        boolean isTestNode,
//        ExtentTestHolder parent,
//        List<ExtentTestHolder> children
//    ) {
//        if (extentTest == null) {
//            throw new IllegalArgumentException("ExtentTest node object must not be null");
//        }
//        this.extentTest = extentTest;
//        this.isTestNode = isTestNode;
//        this.parent = parent;
//        this.children = children != null ? children : new ArrayList<>();
//    }
//
//    public ExtentTestHolder getParent() {
//        return parent;
//    }
//
//    public boolean hasParent() {
//        return parent != null;
//    }
//
//    public List<ExtentTestHolder> getChildren() {
//        return children;
//    }
//
//    public ExtentTest getExtentTest() {
//        return extentTest;
//    }
//
//    public boolean isTestNode() {
//        return isTestNode;
//    }
//
//    public void addChild(ExtentTestHolder testHolder) {
//        children.add(testHolder);
//    }
//}
