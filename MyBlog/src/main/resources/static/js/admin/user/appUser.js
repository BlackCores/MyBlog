var $table = $('#table');
$(function () {
    $(document).on('focus', 'input[type="text"]', function () {
        $(this).parent().find('label').addClass('active');
    }).on('blur', 'input[type="text"]', function () {
        if ($(this).val() == '') {
            $(this).parent().find('label').removeClass('active');
        }
    });
    // bootstrap table初始化
    // http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
    $table.bootstrapTable({
        url: _base + '/user/appuser/list',
        height: getHeight(),    //行高
        striped: true,      //隔行条纹
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType: "undefined",
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                sortName: params.sortName,
                sortOrder: params.sortOrder,
                orderNum: $("#orderNum").val()
            };
            return param;
        },
        // searchOnEnterKey: true,     //搜索按回车
        // showRefresh: true,          //刷新按钮
        // showToggle: true,           //视图切换按钮
        // showColumns: true,          //显示内容下拉
        // showPaginationSwitch: true, //显示分页按钮
        minimumCountColumns: 2,     //最小列数
        clickToSelect: true,        //点击时自动选中
        detailView: true,           //设置为 true 可以显示详细页面模式。
        detailFormatter: 'detailFormatter', //格式化详细页面模式的视图。
        cardView: false,             //设置为 true将显示card详细视图，适用于移动设备。否则为table试图，适用于pc端
        pagination: true,           //设置为 true 会在表格底部显示分页条。
        pageNumber: 1,               //如果设置了分页，首页页码
        pageSize: 10,                //如果设置了分页，页面数据条数
        pageList: [5, 10, 30],         //如果设置了分页，设置可供选择的页面数据条数
        paginationLoop: false,      //设置为 true 启用分页条无限循环的功能。
        classes: 'table table-hover table-no-bordered', //表格的类名称。默认情况下，表格是有边框的，你可以添加 'table-no-bordered' 来删除表格的边框样式。
        sidePagination: 'server',    //设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置服务器数据地址（url）或者重写ajax方法
        silentSort: false,            //设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效
        smartDisplay: false,            //设置为 true 是程序自动判断显示分页信息和 card 视图
        idField: 'userId',                  //指定主键列
        sortName: 'userId',                 //定义排序列，通过url方式获取数据填写字段名，否则填写下标
        sortOrder: 'desc',              //定义排序方式，'asc' 或者 'desc'
        escape: true,                   //转义HTML字符串，替换 &, <, >, ", \`, 和 ' 字符
        // search: true,                   //开启搜索
        // searchOnEnterKey: true,         //设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
        maintainSelected: true,         //设置为 true 在点击分页按钮或搜索按钮时，将记住checkbox的选择项
        buttonsToolbar: '#toolbar',     //一个jQuery 选择器，指明自定义的 toolbar。例如:#toolbar, .toolbar
        columns: [                      //列配置项，详情请查看 列参数 表格
            {checkbox: true},
            {field: 'username', title: '账号', sortable: true, halign: 'center'},
            {
                field: 'status',
                title: '状态',
                sortable: true,
                halign: 'center',
                formatter: function (value, row, index) {
                    return value == 0 ? '<font color="blue">未激活</font>' : value == 1 ? '<font color="green">正常</font>' : value == 3 ? '<font color="red">冻结</font>' : '-';
                }
            },
            {field: 'phone', title: '手机', sortable: true, halign: 'center'},
            {field: 'email', title: '邮箱', sortable: true, halign: 'center'},
            {field: 'ipAddress', title: '登录IP', sortable: true, halign: 'center'},
            {
                field: 'action',
                title: '操作',
                halign: 'center',
                align: 'center',
                formatter: 'actionFormatter',
                events: 'actionEvents',
                clickToSelect: false
            }
        ]
    }).on('load-success.bs.table', function (e, name, args) {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    });


});

function actionFormatter(value, row, index) {
    return [
        '<a class="like" href="javascript:void(0)" data-toggle="tooltip" title="Like"><i class="glyphicon glyphicon-heart"></i></a>　',
        '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
        '<a class="remove ml10" href="javascript:void(0)" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}

window.actionEvents = {
    'click .like': function (e, value, row, index) {
        alert('You click like icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    },
    'click .edit': function (e, value, row, index) {
        alert('You click edit icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    },
    'click .remove': function (e, value, row, index) {
        alert('You click remove icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    }
};

function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

//刷新
function refreshAction() {
    $table.bootstrapTable("refresh");
}

// 新增
function createAction() {
    layer.confirm($('#crudDialog'), {
        anim: 3,
        type: 1
    }, function (index, layero) {//确认
        var formData = $("#formSubmit").serializeJson();
        $.ajax({
            type: "POST",
            url: _base + "/user/appuser/add",
            data: formData,
            dataType: "JSON",
            success: function (data) {
                if (data.success) {
                    layer.msg('成功添加用户：' + formData.username, {
                        icon: 1,
                        time: 1000
                    }, function () {
                        layer.closeAll();
                        $table.bootstrapTable("refresh");
                    });
                } else {
                    layer.msg(data.msg, {
                        icon: 2,
                        time: 1000
                    });
                }
            }
        });

    });

}

// 修改
function updateAction() {
    var rows = $table.bootstrapTable('getSelections');
    if (rows.length == 0) {
        layer.msg('请选择一条记录！', {
            icon: 2,
            time: 1000
        });
    } else if(rows.length > 1){
        layer.msg('只能选择一条记录！', {
            icon: 2,
            time: 1000
        });
    }else{
        $.ajax({
            type: "POST",
            url: _base + "/user/appuser/get/" + rows[0].userId,
            dataType: "JSON",
            success: function (data) {
                //映射数据
                $('#editDiaLog').mapping(data.obj);
                layer.confirm($('#editDiaLog'), {
                    anim: 3,
                    type: 1
                }, function (index, layero) {//确认
                    var password = $("#formSubmit1 input[name='password']").val();
                    $.ajax({
                        type: "POST",
                        url: _base + "/user/appuser/update",
                        data: {password:password,id:rows[0].userId},
                        dataType: "JSON",
                        success: function (data) {
                            if (data.success) {
                                layer.msg('修改成功', {
                                    icon: 1,
                                    time: 1000
                                }, function () {
                                    layer.closeAll();
                                    $table.bootstrapTable("refresh");
                                });
                            } else {
                                layer.msg(data.msg, {
                                    icon: 2,
                                    time: 1000
                                });
                            }
                        }
                    });
                });
            }
        });




    }
}

//激活
function activeAction() {
    var rows = $table.bootstrapTable('getSelections');
    if(rows.length<1){
        layer.msg('请选择一条记录！', {
            icon: 2,
            time: 1000
        });
    }else if(rows.length>1){
        layer.msg('只能选择一条记录！', {
            icon: 2,
            time: 1000
        });
    }else {
        layer.confirm('确认激活用户：' + rows[0].username, function () {
            layer.closeAll();
            $.ajax({
                type: "POST",
                url: _base + "/user/appuser/active",
                data: {id: rows[0].userId},
                dataType: "JSON",
                success: function (data) {
                    if (data.success) {
                        layer.msg('激活成功', {
                            icon: 1,
                            time: 1000
                        }, function () {
                            layer.closeAll();
                            $table.bootstrapTable("refresh");
                        });
                    } else {
                        layer.msg(data.msg, {
                            icon: 2,
                            time: 1000
                        });
                    }
                }
            });
        })
    }
}

// 删除
function deleteAction() {
    var rows = $table.bootstrapTable('getSelections');
    if (rows.length == 0) {
        $.confirm({
            title: false,
            content: '请至少选择一条记录！',
            autoClose: 'cancel|3000',
            backgroundDismiss: true,
            buttons: {
                cancel: {
                    text: '取消',
                    btnClass: 'waves-effect waves-button'
                }
            }
        });
    } else {
        $.confirm({
            type: 'red',
            animationSpeed: 300,
            title: false,
            content: '确认删除该系统吗？',
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        var ids = new Array();
                        for (var i in rows) {
                            ids.push(rows[i].id);
                        }
                        $.alert('删除：id=' + ids.join(","));
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'waves-effect waves-button'
                }
            }
        });
    }
}