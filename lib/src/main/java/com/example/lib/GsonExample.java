package com.example.lib;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;

public class GsonExample {

    private Gson gson = new Gson();

    public void show() {
        A a = new A();
        System.out.println("a.aB.bList.get(0).cString: " + a.aB.bList.get(0).cString);
        String aJson = gson.toJson(a);
        System.out.println("aJson: " + aJson);
        System.out.println("cString fromJson: " + gson.fromJson(aJson, A.class).aB.bList.get(0).cString);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("r", gson.toJsonTree(a));
        JsonObject rJsonObject = jsonObject.getAsJsonObject("r");
        JsonObject aBJsonObject = rJsonObject.getAsJsonObject("aB");
        JsonArray bListJsonArray = aBJsonObject.getAsJsonArray("bList");
        JsonObject firstJsonObject = bListJsonArray.get(0).getAsJsonObject();
        int cInt = firstJsonObject.getAsJsonPrimitive("cInt").getAsInt();
        boolean hasCString = firstJsonObject.has("cString");
        String cString = hasCString ? firstJsonObject.getAsJsonPrimitive("cString").getAsString() : null;
        System.out.println(cInt);
        System.out.println(cString);
    }

    class A {
        int aInt = 1;
        B aB = new B();
    }

    class B {
        int bInt = 22;
        String bString = "bString";
        List<C> bList = Collections.singletonList(new C());
    }

    class C {
        int cInt = 333;
        String cString = "cString";
    }
}
