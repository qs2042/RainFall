package com.qing.erp.common.module.maven;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.val;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class MavenClient {
    private static final String FILE_NAME = "pom.xml";
    private final Document document;

    @SneakyThrows
    private Document getDocument(String fileName) {
        // 创建一个DocumentBuilder工厂对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 解析pom.xml文件并将其转换为DOM对象
        Document document = builder.parse(new File(fileName));
        document.getDocumentElement().normalize();
        return document;
    }

    // 获取document的元素
    private String getElement(String elementName) {
        return document.getElementsByTagName(elementName).item(0).getTextContent();
    }

    // 获取document的元素列表
    private String[] getElementList(String elementName) {
        val nodeList = document.getElementsByTagName(elementName);
        val length = nodeList.getLength();
        String[] elements = new String[length];

        for (int i = 0; i < length; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elements[i] = node.getTextContent();
            }
        }
        return elements;
    }

    private HashMap<String, Object> getElementTree(String elementName) {
        val node = document.getElementsByTagName(elementName).item(0);
        val childNodes = node.getChildNodes();

        val map = new HashMap<String, Object>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            val childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                val v = childNode.getTextContent().trim().replace(" ", "");
                map.put(childNode.getNodeName(), v);
            }
        }
        return map;
    }

    public Map<String, Object> getNodeTree(Node node) {
        Map<String, Object> data = new HashMap<>();

        // 如果node类型是元素node的话
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            // 获取node名称
            data.put("name", node.getNodeName());

            // 该node是否有属性
            if (node.hasAttributes()) {
                Map<String, String> attributes = new HashMap<>();
                NamedNodeMap attributeNodes = node.getAttributes();
                for (int i = 0; i < attributeNodes.getLength(); i++) {
                    Node attr = attributeNodes.item(i);
                    attributes.put(attr.getNodeName(), attr.getNodeValue());
                }
                data.put("attributes", attributes);
            }

            // 该node是否有子node
            if (node.hasChildNodes()) {
                List<Map<String, Object>> children = new ArrayList<>();
                NodeList childNodes = node.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);
                    Map<String, Object> childData = getNodeTree(childNode);
                    if (!childData.isEmpty()) {
                        children.add(childData);
                    }
                }
                // 如果子节点不为空&&如果子节点为空
                if (!children.isEmpty()) {
                    data.put("children", children);
                    // 不管如何还是给它带一个value为null, 防止傻*前端获取属性报错
                    data.put("value", null);
                } else {
                    String value = node.getTextContent().trim();
                    if (!value.isEmpty()) {
                        data.put("value", value);
                    }
                }
            } else {
                // 如果没有子node, 那就说明有值
                String value = node.getTextContent().trim();
                if (!value.isEmpty()) {
                    data.put("value", value);
                }
            }
        }
        return data;
    }
    public Map<String, Object> getNodeTree(String elementName) {
        val node = document.getElementsByTagName(elementName).item(0);
        return getNodeTree(node);
    }

    public MavenClient(String fileName) {
        this.document = getDocument(fileName);
    }

    public static MavenClient build() {
        return new MavenClient(FILE_NAME);
    }

    public static MavenClient build(String fileName) {
        return new MavenClient(fileName);
    }


    // 获取 POM 模型版本号
    public String getModelVersion() {
        return getElement("modelVersion");
    }

    // 获取项目组 ID
    public String getGroupId() {
        return getElement("groupId");
    }

    // 获取项目 ID
    public String getArtifactId() {
        return getElement("artifactId");
    }

    // 获取项目版本号
    public String getVersion() {
        return getElement("version");
    }

    // 获取项目打包类型
    public String getPackaging() {
        return getElement("packaging");
    }

    // 获取项目名称
    public String getName() {
        return getElement("name");
    }

    // 获取项目描述
    public String getDescription() {
        return getElement("description");
    }

    // 获取项目主页 URL
    public String getUrl() {
        return getElement("url");
    }

    // 获取项目构建前提条件
    public Map<String, Object> getPrerequisites() {

        return getNodeTree("prerequisites");
    }

    // 获取项目缺陷管理链接
    public Map<String, Object> getIssueManagement() {
        return getNodeTree("issueManagement");
    }

    // 获取项目父模块信息
    public Map<String, Object> getParent() {
        return getNodeTree("parent");
    }

    // 获取项目子模块列表
    public Map<String, Object> getModules() {
        return getNodeTree("modules");
    }

    // 获取项目配置文件列表
    public Map<String, Object> getProfiles() {
        return getNodeTree("profiles");
    }

    // 获取项目依赖列表
    public Map<String, Object> getDependencies() {
        return getNodeTree("dependencies");
    }

    // 获取项目依赖管理列表
    public Map<String, Object> getDependencyManagement() {
        return getNodeTree("dependencyManagement");
    }

    // 获取项目构建信息
    public Map<String, Object> getBuild() {
        return getNodeTree("build");
    }

    // 获取根节点
    public Map<String, Object> getProject() {
        return getNodeTree("project");
    }

    @SneakyThrows
    public HashMap<String, Object> all() {
        // 获取Maven信息
        return new HashMap<String, Object>() {{
            put("ModelVersion", getModelVersion());
            put("GroupId", getGroupId());
            put("ArtifactId", getArtifactId());
            put("Version", getVersion());
            put("Packaging", getPackaging());

            put("Name", getName());
            put("Description", getDescription());
            put("Url", getUrl());
            put("Prerequisites", getPrerequisites());
            put("IssueManagement", getIssueManagement());

            put("Parent", getParent());
            put("Modules", getModules());
            put("Profiles", getProfiles());
            put("Dependencies", getDependencies());
            put("DependencyManagement", getDependencyManagement());
            put("Build", getBuild());
        }};
    }

    public static void main(String[] args) {
        val mc = MavenClient.build();
        val all = mc.all();
        System.out.println(all);
        System.out.println(new Gson().toJson(all));
    }
}
