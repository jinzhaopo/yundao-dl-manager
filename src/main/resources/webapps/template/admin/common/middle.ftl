<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>显示/隐藏菜单</title>
		[#include "/admin/include/head.htm"]
		<style type="text/css">
			html, body {
				height: 100%;
				overflow: hidden;
			}
		</style>
		<script type="text/javascript">
			$().ready(function() {
				$(".main").click(function() {
					var frameset = window.parent.window.document.getElementById("frameset");
					if (frameset.cols == "210,5,*") {
						frameset.cols = "0,5,*";
						$(".main").removeClass("leftArrow");
						$(".main").addClass("rightArrow");
						//Cookie.set("userDeptTree_switch", "close", 3600*100*360);
					} else {
						frameset.cols = "210,5,*";
						$(".main").removeClass("rightArrow");
						$(".main").addClass("leftArrow");
						//Cookie.set("userDeptTree_switch", "on", 3600*100*360);
					}
				});

				//从cookie中取是否开关的值进行判断
				var userDeptTree_switch = Cookie.find("userDeptTree_switch");
				if (userDeptTree_switch && userDeptTree_switch == "on") {
					var frameset = window.parent.window.document.getElementById("frameset");
					frameset.cols = "210,5,*";
					$(".main").removeClass("rightArrow");
					$(".main").addClass("leftArrow");
				}
			})
		</script>
	</head>
	<body class="middle inner">
		<div class="main rightArrow" title="部门树选择"></div>
	</body>
</html>