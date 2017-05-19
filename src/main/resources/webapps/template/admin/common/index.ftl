<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>欢迎使用${setting.name}旅游云后台管理系统</title>
		<#include "/admin/include/head.htm">
		<script type="text/javascript" src="${base}/resource/js/changepassword.js"></script>
		<script type="text/javascript" src="${base}/resource/js/login.js"></script>
		<link rel="stylesheet" href="${base}/resource/css/index.css" type="text/css" />
	</head>
	<body style="text-align:center; background:#F0F0F0; overflow:hidden;">
		<div id="pageloading" style="display:block;"></div>
		<div id="topmenu" class="l-topmenu">
			<div class="l-topmenu-logo">
				${setting.name}后台管理系统
			</div>
			<div class="l-topmenu-welcome">
				<span class="l-topmenu-username"></span>欢迎您  &nbsp;
				[<a href="javascript:f_changepassword()">修改密码 </a>] &nbsp;

				[<a href="javascript:f_login()">切换用户</a>]
				[<a href="${base}/admin/logout.jhtml">退出</a>]
			</div>
		</div>
		<div id="mainbody" class="l-mainbody" style="width:99.2%; margin:0 auto; margin-top:3px;" >
			<div position="left" title="主要菜单" id="mainmenu"></div>
			<div position="center" id="framecenter">
				<div tabid="home" title="我的主页">
					<iframe frameborder="0" name="home" id="home" src="${base}/admin/welcome.jhtml"></iframe>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			//几个布局的对象
			var layout, tab, accordion;
			//tabid计数器，保证tabid不会重复
			var tabidcounter = 0;
			//窗口改变时的处理函数
			function f_heightChanged(options) {
				if (tab)
					tab.addHeight(options.diff);
				if (accordion && options.middleHeight - 24 > 0)
					accordion.setHeight(options.middleHeight - 24);
			}

			//增加tab项的函数
			function f_addTab(tabid, text, url) {
				if (!tab)
					return;
				if (!tabid) {
					tabidcounter++;
					tabid = "tabid" + tabidcounter;
				}
				tab.addTabItem({
					tabid : tabid,
					text : text,
					url : url
				});
			}

			function getTabById(tabid) {
				if (!tabid) {
					alert("tab id 为空");
					return;
				}

				var tab = $("#framecenter > .l-tab-content > .l-tab-content-item[tabid=" + tabid + "]");
				if (!tab) {
					alert("tabid【" + tabid + "】 无法找到")
					return;
				}
				return tab;
			}

			//登录
			function f_login() {
				LG.login();
			}

			//修改密码
			function f_changepassword() {
				LG.changepassword();
			}

			//切换页面
			function f_switchTab(tabid) {
				tab.selectTabItem(tabid);
			}

			//判断页面是否存在
			function f_isTabItemExist(tabid) {
				return tab.isTabItemExist(tabid);
			}

			//关闭当前标签
			function f_closeCurrentTab() {
				tab.removeSelectedTabItem();
			}


			$(document).ready(function() {
				// 登录页面若在框架内，则关闭当前tab页
				if (self != top) {
					var win = parent || window;
					win.LG.closeCurrentTab();
					return;
				}
				//系统更新提示
				var updateContent = "${updateContent}";
				if (updateContent.isNotEmpty()) {
					LG.showError(updateContent);
				}
				//菜单初始化
				$("ul.menulist li").live('click', function() {
					var jitem = $(this);
					var tabid = jitem.attr("menuNo");
					var url = jitem.attr("url");
					if (!url)
						return;
					if (!tabid) {
						tabidcounter++;
						tabid = "tabid" + tabidcounter;
						jitem.attr("tabid", tabid);

						//给url附加menuno
						if (url.indexOf('?') > -1)
							url += "&";
						else
							url += "?";
						url += "MenuNo=" + jitem.attr("menuNo");
						jitem.attr("url", url);
					}
					f_addTab(tabid, $("span:first", jitem).html(), url);
				}).live('mouseover', function() {
					var jitem = $(this);
					jitem.addClass("over");
				}).live('mouseout', function() {
					var jitem = $(this);
					jitem.removeClass("over");
				});

				//布局初始化
				//layout
				layout = $("#mainbody").ligerLayout({
					height : '100%',
					heightDiff : -3,
					leftWidth : 140,
					onHeightChanged : f_heightChanged,
					minLeftWidth : 120
				});
				var bodyHeight = $(".l-layout-center:first").height();
				//Tab
				tab = $("#framecenter").ligerTab({
					height : bodyHeight,
					contextmenu : true
				});

				//预加载dialog的背景图片
				LG.prevDialogImage();
				var mainmenu = $("#mainmenu");
				$.getJSON(base + '/admin/menu/loadLeftMenu.jhtml?data=' + Math.random(), function(menus) {
					var menus = assemblyTreeObj(menus);
					$(menus).each(function(i, menu) {
						var item = $('<div title="' + menu.name + '"><ul class="menulist"></ul></div>');
						$(menu.childs).each(function(j, submenu) {
							var subitem = $('<li><img/><span></span><div class="menuitem-l"></div><div class="menuitem-r"></div></li>');
							subitem.attr({
								url : base + submenu.mUrl,
								tabid : submenu.menuNo,
								menuno : submenu.menuNo
							});
							if (!submenu.menuIcon) {
								$("img", subitem).attr("src", base + "/resource/images/icons/32X32/my_account.gif");
							} else {
								$("img", subitem).attr("src", base + "/resource/images/icons/32X32/" + submenu.moduleIcon);
							}

							$("span", subitem).html(submenu.name);
							$("ul:first", item).append(subitem);
						});
						mainmenu.append(item);
					});

					//Accordion
					accordion = mainmenu.ligerAccordion({
						height : bodyHeight - 24,
						speed : null
					});
					$("#pageloading").hide();
				});
			});
		</script>
	</body>
</html>