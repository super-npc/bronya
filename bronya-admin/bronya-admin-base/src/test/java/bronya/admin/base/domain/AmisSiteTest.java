//package bronya.admin.base.domain;
//
//
//import bronya.admin.base.module.amis.dto.AmisSiteGroup;
//import com.alibaba.fastjson2.JSONArray;
//import org.dromara.hutool.json.JSONUtil;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//class AmisSiteTest {
//    String json = """
//            [
//                  {
//                    "label": "Home",
//                    "url": "/",
//                    "redirect": "/index/1"
//                  },
//                  {
//                    "label": "示例",
//                    "children": [
//                      {
//                        "label": "页面A",
//                        "url": "index",
//                        "schema": {
//                          "type": "page",
//                          "title": "页面A",
//                          "body": "页面A"
//                        },
//                        "children": [
//                          {
//                            "label": "页面A-1",
//                            "url": "1",
//                            "schema": {
//                              "type": "page",
//                              "title": "页面A-1",
//                              "body": "页面A-1"
//                            }
//                          },
//                          {
//                            "label": "页面A-2",
//                            "url": "2",
//                            "schema": {
//                              "type": "page",
//                              "title": "页面A-2",
//                              "body": "页面A-2"
//                            }
//                          },
//                          {
//                            "label": "页面A-3",
//                            "url": "3",
//                            "schema": {
//                              "type": "page",
//                              "title": "页面A-3",
//                              "body": "页面A-3"
//                            }
//                          }
//                        ]
//                      },
//                      {
//                        "label": "页面B",
//                        "badge": 3,
//                        "badgeClassName": "bg-info",
//                        "schema": {
//                          "type": "page",
//                          "title": "页面B",
//                          "body": "页面B"
//                        }
//                      },
//                      {
//                        "label": "页面C",
//                        "schema": {
//                          "type": "page",
//                          "title": "页面C",
//                          "body": "页面C"
//                        }
//                      },
//                      {
//                        "label": "列表示例",
//                        "url": "/crud",
//                        "rewrite": "/crud/list",
//                        "icon": "fa fa-cube",
//                        "children": [
//                          {
//                            "label": "列表",
//                            "url": "/crud/list",
//                            "icon": "fa fa-list",
//                            "schemaApi": "get:/pages/crud-list.json"
//                          },
//                          {
//                            "label": "新增",
//                            "url": "/crud/new",
//                            "icon": "fa fa-plus",
//                            "schemaApi": "get:/pages/crud-new.json"
//                          },
//                          {
//                            "label": "查看",
//                            "url": "/crud/:id",
//                            "schemaApi": "get:/pages/crud-view.json"
//                          },
//                          {
//                            "label": "修改",
//                            "url": "/crud/:id/edit",
//                            "schemaApi": "get:/pages/crud-edit.json"
//                          }
//                        ]
//                      }
//                    ]
//                  },
//                  {
//                    "label": "分组2",
//                    "children": [
//                      {
//                        "label": "用户管理",
//                        "schema": {
//                          "type": "page",
//                          "title": "用户管理",
//                          "body": "页面C"
//                        }
//                      },
//                      {
//                        "label": "外部链接",
//                        "link": "http://baidu.gitee.io/amis"
//                      },
//                      {
//                        "label": "部门管理",
//                        "schemaApi": "${API_HOST}/api/amis-mock/mock2/service/form?tpl=tpl3"
//                      },
//                      {
//                        "label": "jsonp 返回示例",
//                        "schemaApi": "jsonp:/pages/jsonp.js?callback=jsonpCallback"
//                      }
//                    ]
//                  }
//                ]
//
//            """;
//    @Test
//    public void gen(){
//        List<AmisSiteGroup> amisSites = JSONArray.parseArray(json, AmisSiteGroup.class);
//        System.out.println("bean = " + amisSites);
//        String jsonStr = JSONUtil.toJsonStr(amisSites);
//        System.out.println("jsonStr = " + jsonStr);
//    }
//}