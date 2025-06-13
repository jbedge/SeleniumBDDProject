//package com.report;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//public class ExtentTestManager {
//    private static final Logger logger = LoggerFactory.getLogger(ExtentTestManager.class);
//
//    private static ExtentTestManager instance;
//    // map for all extent test objects where the key is MetaTest
//    private static Map < Object, ExtentTestHolder > allNodeMap = Collections.synchronizedMap(new HashMap < Object, ExtentTestHolder > ());
//    // see comments on MetaTest. Inheritable for tng test timeout and other child thread possibilities such as monitor thread for axon
//    private static final InheritableThreadLocal < ExtentTestHolder > threadExtentTestHolder = new InheritableThreadLocal < > ();
//    private ExtentTestManager() {
//
//    }
//
//    public static synchronized ExtentTestManager getInstance() {
//        if (instance == null) {
//            instance = new ExtentTestManager();
//        }
//        return instance;
//    }
//
//    private ExtentReports getExtentReports() {
//        return ExtentReportsManager.getInstance().getExtentReports();
//    }
//
//    private boolean addIfNotExisting(final Object key, final Object parentKey, boolean isTestNode, String name, String description) {
//
//        if (key == null) {
//            throw new IllegalArgumentException(
//                "An ExtentTest node can not be created with a null key.");
//        }
//
//        synchronized(allNodeMap) {
//
//            if (!hasWithKey(key)) {
//
//                logger.debug("Adding new ExtentTest with name [{}] and key [{}], with parent key [{}].",
//                    name,
//                    key,
//                    parentKey);
//
//                if (parentKey != null && !hasWithKey(parentKey)) {
//                    throw new IllegalArgumentException(
//                        "An ExtentTest node has not been created for the parent key [" + parentKey + "]");
//                }
//
//                if (description == null) {
//                    description = "";
//                }
//
//                if (name == null || name.trim().isEmpty()) {
//                    throw new IllegalArgumentException(
//                        "An ExtentTest node can not be created with the name [" + name + "]");
//                }
//
//                ExtentTestHolder newTestHolder;
//
//                if (parentKey != null) { // we have a parent already verified as existing
//
//                    ExtentTestHolder parentHolder = allNodeMap.get(parentKey);
//                    ExtentTest newTest = parentHolder.getExtentTest().createNode(name, description);
//                    newTestHolder = new ExtentTestHolder(newTest, isTestNode, parentHolder, null);
//                    parentHolder.addChild(newTestHolder);
//                } else {
//
//                    ExtentTest newTest = getExtentReports().createTest(name, description);
//                    newTestHolder = new ExtentTestHolder(newTest, isTestNode, null, null);
//                }
//
//                assert newTestHolder != null: "Error creating ExtentTest object.";
//
//                setCurrentThreadTest(newTestHolder);
//                allNodeMap.put(key, newTestHolder);
//
//                return true;
//
//            } else { // already exists
//                logger.trace("An ExtentTest already exists for the key provided [{}].", key);
//                return false; // not added
//            }
//        }
//
//    }
//
//    public boolean addTestIfNotExisting(final MetaTest key, final Object parentKey) {
//        synchronized(allNodeMap) {
//
//            if (!hasWithKey(key)) {
//
//                return addIfNotExisting(key, parentKey, true, key.getReportTestName(), key.getReportTestDescription());
//            } else {
//
//                if (hasTestWithKey(key)) {
//                    // Consider validating that the existing test has the same parent if there are problems
//                    return false;
//                } else {
//
//                    // Key exists but does not match node type specified
//                    throw new IllegalArgumentException("The key provided for a new test " +
//                        "is already in use as the key for a hierarchy node. Key:  " + key);
//                }
//            }
//        }
//    }
//
//    public boolean addHierarchyNodeIfNotExisting(final Object key, final Object parentKey, String name, String description) {
//
//        synchronized(allNodeMap) {
//
//            if (!hasWithKey(key)) {
//
//                return addIfNotExisting(key, parentKey, false, name, description);
//            } else {
//
//                if (hasHierarchyNodeWithKey(key)) {
//
//                    return false;
//                } else {
//
//                    // key exists but does not match node type specified
//                    throw new IllegalArgumentException("The key provided for a new hierarchy node " +
//                        "is already in use as the key for a test. Key: " + key);
//                }
//            }
//        }
//
//    }
//
//    public boolean hasTestWithKey(final Object key) {
//
//        if (allNodeMap.containsKey(key)) {
//            return allNodeMap.get(key).isTestNode();
//        } else {
//            return false;
//        }
//
//    }
//
//    public boolean hasHierarchyNodeWithKey(final Object key) {
//
//        if (allNodeMap.containsKey(key)) {
//            return !allNodeMap.get(key).isTestNode();
//        } else {
//            return false;
//        }
//
//    }
//    public ExtentTest getTestWithKey(final MetaTest key) {
//
//        if (hasTestWithKey(key)) {
//            setCurrentThreadTest(allNodeMap.get(key));
//            return allNodeMap.get(key).getExtentTest();
//        } else {
//            if (hasWithKey(key)) {
//
//                // key exists but does not match node type specified
//                throw new IllegalArgumentException("The key specified to retrieve a test " +
//                    "exists but is key for a node of a different type. Key : " + key);
//            } else {
//
//                // key does not exist
//                throw new IllegalArgumentException("No Extent test object exists" +
//                    "for the key specified. Key : " + key);
//            }
//        }
//
//    }
//
//    public ExtentTest getHierarchyNodeWithKey(final Object key) {
//
//        if (hasHierarchyNodeWithKey(key)) {
//            setCurrentThreadTest(allNodeMap.get(key));
//            return allNodeMap.get(key).getExtentTest();
//        } else {
//            if (hasWithKey(key)) {
//
//                // key exists but does not match node type specified
//                throw new IllegalArgumentException("The key specified to retrieve a hierarchy node " +
//                    "exists but is key for a node of a different type. Key : " + key);
//            } else {
//
//                // key does not exist
//                throw new IllegalArgumentException("No Extent hierarchy node object exists" +
//                    "for the key specified. Key : " + key);
//            }
//        }
//
//    }
//
//    private boolean hasWithKey(final Object key) {
//
//        return allNodeMap.containsKey(key);
//    }
//
//    /**
//     * If isTestNode is true set returned should contain only test nodes.
//     * False for only hierarchy nodes.
//     * Null for all tests.
//     *
//     * @param isTestNode
//     * @return
//     */
//    private Set < ExtentTest > getAllByType(Boolean isTestNode) {
//
//        Set < ExtentTest > testSet = new HashSet < > ();
//
//        synchronized(allNodeMap) {
//
//            Collection < ExtentTestHolder > coll = allNodeMap.values();
//            for (ExtentTestHolder hol: coll) {
//
//                assert hol != null: "Extent test node manager contains null test holder reference.";
//                ExtentTest t = hol.getExtentTest();
//                assert t != null: "Extent test node manager contains holder with null extent test reference.";
//
//                if (isTestNode == null || (isTestNode && hol.isTestNode()) || (!isTestNode && !hol.isTestNode())) {
//                    boolean added = testSet.add(t);
//                    assert added: "Extent test node manager contains duplicate entry of extent test: " + t;
//                }
//            }
//
//            logger.trace("Returning [{}] extent test nodes out of possible [{}] for filter isTestNode [{}].",
//                testSet.size(),
//                coll.size(),
//                isTestNode);
//        }
//
//        return Collections.unmodifiableSet(testSet);
//    }
//
//    public Set < ExtentTest > getAllTests() {
//
//        return getAllByType(true);
//    }
//
//    public Set < ExtentTest > getAllHierarchyNodes() {
//
//        return getAllByType(false);
//    }
//
//    /**
//     * The last {@link ExtentTest} active on the current thread.
//     * Throws a runtime exception if a test is not active (use {@link #hasCurrentThreadTest()}).
//     *
//     * An {@link ExtentTest} is considered active when it is added or retrieved.
//     * A test remains active on the thread until it is replaced by another test,
//     * or explicitly unset by {@link #unsetCurrentThreadTest()}.
//     *
//     * @return
//     */
//    public ExtentTest getCurrentThreadTest() {
//        ExtentTest test = threadExtentTestHolder.get().getExtentTest();
//
//        if (test == null) {
//            throw new AssertionError("ExtentTest Object not set for current thread [id: " + Thread.currentThread().getId() + ", name: " + Thread.currentThread().getName() + "].");
//        }
//
//        return test;
//    }
//
//    public boolean hasCurrentThreadTest() {
//        return threadExtentTestHolder.get() != null;
//    }
//
//    public void unsetCurrentThreadTest() {
//        logger.trace("Unsetting ExtentTest for current thread.");
//        threadExtentTestHolder.set(null);
//    }
//    private void setCurrentThreadTest(ExtentTestHolder test) {
//
//        if (test == null) {
//            throw new IllegalArgumentException("Parameter must not be null.");
//        }
//
//        if (!test.equals(threadExtentTestHolder.get())) {
//
//            logger.trace("Setting ExtentTest for current thread.");
//            threadExtentTestHolder.set(test);
//        }
//    }
//
//}