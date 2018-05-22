$(function () {
    var table = $('#example').DataTable({
        serverSide: true,
        processing: true,
        paging:true,
        ordering:true,
        bAutoWidth : true,//自动计算列宽
        bPaginate :true,//是否开启分页
        bServerSide : true,//使用服务器数据
        bProcessing: true,//加载缓冲效果
        bLengthChange : true, //是否显示查询条数的下拉框
        filter : false, //是否开搜索

        deferRender: true,
        iDisplayLength :10, //默认查询多少条
        ajax:_base+'/static/test.json',
        aLengthMenu: [
            [5, 10, 25, 50, -1],
            [5, 10, 25, 50, "全部"]
        ],
        columns: [
            { "data": "name" },
            { "data": "age" },
            { "data": "sex" },
            { "data": "time" }
        ],
        columnDefs:[
            {
                targets: 2,
                title: "最后更新时间",
                render: function (data, type, row, meta) {
                    return data+'_5555';
                }
            }
        ],
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        }
    });
    console.log($(window).height())
    console.log(document.documentElement.clientHeight - 118)
})