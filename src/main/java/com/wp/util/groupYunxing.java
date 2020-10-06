package com.wp.util;

import com.wp.entity.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class groupYunxing {

    public static Map<String, List<PageData>> get(List<PageData> list, String time) throws Exception{
        Map<String, List<PageData>> resultMap = new HashMap<String, List<PageData>>();

        try{
            for(PageData pd : list){

                if(resultMap.containsKey( pd.getString(time) ) ){//map中time已存在，将该数据存放到同一个key（key存放的是time）的map中
                    resultMap.get(pd.getString(time)).add(pd);
                }else{//map中不存在，新建key，用来存放数据
                    List<PageData> tmpList = new ArrayList<PageData>();
                    tmpList.add(pd);
                    resultMap.put(pd.getString(time), tmpList);

                }

            }

        }catch(Exception e){
            throw new Exception("按照time对已有数据进行分组时出现异常", e);
        }

        return resultMap;
    }
}
