var _base = '';

$(function() {
	// Waves初始化
	Waves.displayEffect();
	// 数据表格动态高度
	$(window).resize(function () {
		$('#table').bootstrapTable('resetView', {
			height: getHeight()
		});
	});
	// 设置input特效
	$(document).on('focus', 'input[type="text"]', function() {
		$(this).parent().find('label').addClass('active');
	}).on('blur', 'input[type="text"]', function() {
		if ($(this).val() == '') {
			$(this).parent().find('label').removeClass('active');
		}
	});
	// select2初始化
	$('select').select2();

    //layer初始化
    layer.config({
        // skin: 'col-xs-12 col-sm-10 col-md-8 col-lg-6',	//初始化样式
		// icon: 1/2/3/4/5								//图标
		// time: 1000									//自动关闭毫秒
        skin: 'col-xs-12 col-md-8',						//初始化样式
        maxWidth:'100%',								//最大宽度
        shadeClose:true,								//点击空白处关闭
        btn: ['确认', '取消'],							//默认按钮

    });

});

// 动态高度
function getHeight() {
	return $(window).height() - 20;
}
// 数据表格展开内容
function detailFormatter(index, row) {
	var html = [];
	$.each(row, function (key, value) {
		html.push('<p><b>' + key + ':</b> ' + value + '</p>');
	});
	return html.join('');
}
// 初始化input特效
function initMaterialInput() {
	$('form input[type="text"]').each(function () {
		if ($(this).val() != '') {
			$(this).parent().find('label').addClass('active');
		}
	});
}

//json对象转换
$.fn.serializeJson = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//将数据映射到input表单
$.fn.mapping = function (data) {
	$(this).find('label').addClass('active')
	var it = $(this).find('input');
	for(var i = 0;i<it.length;i++){
        for(t in data){
			if(it[i]['name']==t){
                it[i]['value']=data[t];
			}
        }
	}
};