package com.vexsoftware.votifier.model;


import com.google.gson.JsonObject;

public class Vote {
    private String serviceName;
    private String username;
    private String address;
    private String timeStamp;

    @Deprecated
    public Vote() {}

    public Vote(String serviceName, String username, String address, String timeStamp) {
        this.serviceName = serviceName;
        this.username = username;
        this.address = address;
        this.timeStamp = timeStamp;
    }

    private static String getTimestamp(JsonObject object) {
        try {
            return Long.toString(object.get("timestamp").getAsLong());
        } catch (Exception e) {
            return object.get("timestamp").getAsString();
        }
    }

    public Vote(JsonObject jsonObject) {
        this(jsonObject.get("serviceName").getAsString(), jsonObject.get("username").getAsString(), jsonObject.get("address").getAsString(), getTimestamp(jsonObject));
    }

    public String toString() {
        return "Vote (from:" + serviceName + " username:" + username + " address:" + address + " timeStamp:" + timeStamp + ")";
    }

    @Deprecated
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Deprecated
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Deprecated
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Deprecated
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public JsonObject serialize() {
        JsonObject ret = new JsonObject();
        ret.addProperty("serviceName", serviceName);
        ret.addProperty("username", username);
        ret.addProperty("address", address);
        ret.addProperty("timestamp", timeStamp);
        return ret;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Vote vote = (Vote)o;

        if (!serviceName.equals(vote.serviceName))
            return false;
        if (!username.equals(vote.username))
            return false;
        if (!address.equals(vote.address))
            return false;
        return timeStamp.equals(timeStamp);
    }


    public int hashCode() {
        int result = serviceName.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + timeStamp.hashCode();
        return result;
    }
}
