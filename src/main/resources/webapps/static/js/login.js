LG.login = function ()
{
    $(document).bind('keydown.login', function (e)
    {
        if (e.keyCode == 13)
        {
            dologin();
        }
    });

    if (!window.loginWin)
    {
        var loginPanle = $("<form id='loginForm' action='" + base + "/admin/loginVerify?type=switch' method='post'></form>");
        loginPanle.ligerForm({
            fields: [
                { display: '用户名', name: 'j_username' },
                { display: '密码', name: 'j_password', type: 'password' }
            ]
        });

        window.loginWin = $.ligerDialog.open({
            width: 400,
            height: 140, top: 200,
            isResize: true,
            title: '用户登录',
            target: loginPanle,
            buttons: [
            { text: '登录', onclick: function ()
            {
                dologin();
            } 
            },
            { text: '取消', onclick: function ()
            {
                window.loginWin.hide();
                $(document).unbind('keydown.login');
            } 
            }
            ]
        });
    }
    else
    {
        window.loginWin.show();
    }

    function dologin()
    {
        var username = $("#j_username").val();
        var password = $("#j_password").val();
		if(username == ''){
			LG.showError('请输入用户名!');
			return;
		}
		if(password == ''){
			LG.showError('请输入密码!');
			return;
		}
		
		$("#loginForm").submit();
    }

};