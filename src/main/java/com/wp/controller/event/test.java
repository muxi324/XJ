package com.wp.controller.event;

import net.sf.json.JSONArray;

public class test {
    public static void main(String[] args) {
        String src = "[{\n" +
                "\"work_ID\":\"B1\",\n" +
                "\"work_name\":\"温度\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\",\n" +
                "\"work_note\":[{\n" +
                "\"note_name\":\"正常范围\",\n" +
                "\"note_content\":\"10-15\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\"\n" +
                "},{\n" +
                "\"note_name\":\"异常标准\",\n" +
                "\"note_content\":\"过大或过小\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\"\n" +
                "},{\n" +
                "\"note_name\":\"特殊提示\",\n" +
                "\"note_content\":\"避免烫伤\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\"\n" +
                "}],\n" +
                "\"view\":[{\n" +
                "\"view_class\":\"拍照\",\n" +
                "\"view_name\":\"温度拍照\",\n" +
                "\"font_size\":20,\n" +
                "\"font_color\":\"#080808\"\n" +
                "},{\n" +
                "\"view_class\":\"输入框\",\n" +
                "\"view_name\":\"输入温度\",\n" +
                "\"font_size\":20,\n" +
                "\"font_color\":\"#080808\"\n" +
                "}]\n" +
                "},{\n" +
                "\"work_ID\":\"B2\",\n" +
                "\"work_name\":\"仪表参数\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\",\n" +
                "\"work_note\":[{\n" +
                "\"note_name\":\"正常范围\",\n" +
                "\"note_content\":\"10-15\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\"\n" +
                "},{\n" +
                "\"note_name\":\"异常标准\",\n" +
                "\"note_content\":\"过大或过小\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\"\n" +
                "},{\n" +
                "\"note_name\":\"特殊提示\",\n" +
                "\"note_content\":\"避免烫伤\",\n" +
                "\"font_color\":\"#6699FF\",\n" +
                "\"font_size\":\"20\"\n" +
                "}],\n" +
                "\"view\":[{\n" +
                "\"view_class\":\"拍照\",\n" +
                "\"view_name\":\"仪表拍照\",\n" +
                "\"font_size\":20,\n" +
                "\"font_color\":\"#080808\"\n" +
                "},{\n" +
                "\"view_class\":\"输入框\",\n" +
                "\"view_name\":\"输入仪表数字\",\n" +
                "\"font_size\":20,\n" +
                "\"font_color\":\"#080808\"\n" +
                "}]\n" +
                "}]";
        JSONArray array = JSONArray.fromObject(src);
    }
}
