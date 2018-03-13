/**
 * Created by wp on 2017/12/18.
 */
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});
var TableInit = function () {
    var oTableInit = new Object();
//     alert('gg');
    //初始化Table
    oTableInit.Init = function () {
        $('#mission_msg').bootstrapTable({
            url: '<%=basePath%>querytask/listMissions.do',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            // height: 550,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "mission_id",                 //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{checkbox: true},
                {
                    field: 'checkbox', title: '序号', formatter: function (value, row, index) {
                    return index + 1;
                }
                },
                {field: 'flow_number', title: '任务联单号', editable: true, align: 'center', valign: 'middle'},
                {field: 'worker_name', title: '安装人员', align: 'center', valign: 'middle'},
                {field: 'send_time', title: '下达时间', align: 'center', valign: 'middle', visible: false},
                {field: 'house_address', title: '房源地址', align: 'center', valign: 'middle'},
                {field: 'house_owner_name', title: '房源联系人', align: 'center', valign: 'middle'},

                {field: 'mission_condition', title: '任务状态', align: 'center', valign: 'middle'},
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    events: operateEvents,
                    formatter: function () {
                        return [
                            '<a class="detail" href="#" title="manage">',
                            '<i class="glyphicon glyphicon-eye-open"></i> 详情',
                            '</a>', '  ',
                        ].join('');
                    }
                }
            ]

        });
    };
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
            };
            return temp;
        };
        return oTableInit;

};
var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};

window.operateEvents = {
    'click .detail': function (e, value, row) {
        window.open("<%=basePath>mission/detail.do" + row.mission_id);
    }
}