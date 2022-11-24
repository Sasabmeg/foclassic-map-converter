package net.fodev.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CritterProto implements Comparable<CritterProto> {
    private String header;
    private Integer pid;
    private Map<String, String> attributes;

    public CritterProto() {
        attributes = new LinkedHashMap<>();
    }

    public void add(String key, String value) {
        attributes.put(key, value);
    }

    public String get(String key) {
        return attributes.get(key);
    }

    public boolean containsKey(String key) {
        return attributes.containsKey(key);
    }

    public boolean sameMainAttributes(CritterProto other) {
        String[] mainAttributes = {"ST_BASE_CRTYPE", "ST_BODY_TYPE"};
        for (String attr :mainAttributes) {
            if (!attributeEquals(attr, other)) {
                return false;
            }
        }
        return true;
    }

    private boolean attributeEquals(String attribute, CritterProto other) {
        if (get(attribute) == null && other.get(attribute) == null) {
            return true;
        } else if (get(attribute) == null || other.get(attribute) == null) {
            return false;
        } else if (get(attribute).equalsIgnoreCase(other.get(attribute)) ) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public int compareTo(CritterProto o) {
        return this.getPid() - o.getPid();
    }

    @Override
    public String toString() {
        String ret = header + "\n";
        ret += String.format("Pid=%d\n", pid);
        /*
        for (Map.Entry<String, String> kv : attributes.entrySet()) {
            ret += String.format("%s=%s\n", kv.getKey(), kv.getValue());
        }
         */
        String result = ret + attributes.entrySet().stream()
                .map(s -> String.format("%s=%s\n", s.getKey(), s.getValue()))
                .collect(Collectors.joining());

        return result;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
