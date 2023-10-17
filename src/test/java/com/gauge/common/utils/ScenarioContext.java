package com.gauge.common.utils;

import com.thoughtworks.gauge.datastore.DataStoreFactory;

public class ScenarioContext {

    public static Object getData(String key){
        return DataStoreFactory.getScenarioDataStore().get(key);
    }

    public static void setData(String key, String data){
        DataStoreFactory.getScenarioDataStore().put(key, data);
    }
}
