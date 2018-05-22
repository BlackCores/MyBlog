//解决套页面
if (top != window) {
    top.location.href = window.location.href;
}
$(function() {
    // Waves初始化
	Waves.displayEffect();
	// 输入框获取焦点后出现下划线
	$('.form-control').focus(function() {
		$(this).parent().addClass('fg-toggled');
	}).blur(function() {
		$(this).parent().removeClass('fg-toggled');
	});
});
Checkbix.init();
$(function() {
	// 点击登录按钮
	$('#login-bt').click(function() {
		login();
	});
	// 回车事件
	$('#username, #password').keypress(function (event) {
		if (13 == event.keyCode) {
			login();
		}
	});
});
// 登录
function login() {
	var loginLoad = layer.load();
	$.ajax({
		url: _base + '/login',
		type: 'POST',
		data: {
			username: $('#username').val(),
			password: $('#password').val(),
			rememberMe: $('#rememberMe').is(':checked'),
		},
		beforeSend: function() {
		},
		success: function(json){
			layer.close(loginLoad);
			if(json.success){
				layer.msg('登录成功', {
					icon: 1,
					time: 1000
				}, function(){
					window.location=_base+'/index';
				});
			}else{
                layer.msg(json.msg, {
                    icon: 2,
                    time: 1000
                }, function(){
                    $('#password').val('');
                });
			}
        },
		error: function(error){
            layer.close(loginLoad);
			console.log(error);
		}
	});
}