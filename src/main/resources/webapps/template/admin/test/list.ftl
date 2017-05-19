<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>list</title> <#include "/admin/include/head.htm"> <script
		type="text/javascript">
		var grid;
		$(function() {
			grid = $("#maingrid")
					.ligerGrid(
							{
								columns : [
										{
											name : 'id',
											hide : true,
											width : 0,
											isAllowHide : false
										},
										{
											display : '用户名',
											name : 'name',
											align : 'center',
											width : 80
										}],
								pageSize : 20,
								url : '${base}/admin/queryData.jhtml',
								toolbar : {},
								width : '100%',
								height : '100%',
								enabledSort : true
							});
			$("#pageloading").hide();
			LG.loadToolbar(grid);

			//绑定搜索按钮
			LG.search(grid);
		});
		//新增
		function addUser() {
			top.f_addTab(null, '新增会员', base + '/admin/member/add.jhtml');
		}

		//编辑账号
		function editMember(member_id) {
			top.f_addTab(null, '编辑会员', base + '/admin/member/edit/' + member_id
					+ '.jhtml?' + "&parentMenuNo=member_list");
		}
		//更改会员资料
		function toverify() {
			top.f_addTab(null, '更改会员资料', base
					+ "/admin/member/toVerify.jhtml?parentMenuNo=member_list");
		}

		//查看用户收支明细
		function showPayDetail(id, userName) {
			top.f_addTab("member" + id, '用户【' + userName + '】收支明细', base
					+ '/admin/payment/list/' + id
					+ '.jhtml?parentMenuNo=member_list');
		}
		//查看消费记录
		function usedLog(id, name, card_num) {
			var str = card_num;
			top.f_addTab("member" + id, '用户【' + name + '】消费明细', base
					+ "/admin/order/list/" + str
					+ ".jhtml?parentMenuNo=member_list");
		}

		//删除
		function f_delete() {
			var selecteds = grid.getSelecteds();
			if (selecteds.length > 0) {
				LG.ajax({
					url : base + '/admin/member/delete.jhtml',
					loading : '正在删除中...',
					data : {
						ids : LG.getIds(selecteds)
					},
					success : function() {
						LG.showSuccess('删除成功');
						f_reload();
					},
					error : function(message) {
						LG.showError(message);
					}
				});
			} else {
				LG.tip('请选择行!');
			}
		}

		function f_forbid() {//禁用
			var selecteds = grid.getSelecteds();
			if (selecteds.length > 0) {
				var arr = [];
				for (var i = 0; i < selecteds.length; i++) {
					arr.push(selecteds[i].id);
				}
				LG.ajax({
					url : base + '/admin/member/forbid.jhtml',
					loading : '正在操作中...',
					data : {
						ids : arr
					},
					success : function() {
						LG.showSuccess('禁用成功');
						f_reload();
					},
					error : function(message) {
						LG.showError(message);
					}
				});
			} else {
				LG.tip('请选择行!');
			}
		}
		//下载
		function download() {
			var drawType = $("#drawType").val();
			var drawState = $("#drawState").val();
			var startDate = $("#startTime").val();
			var endDate = $("#endTime").val();
			window
					.open(base
							+ '/admin/download/getInputStream.jhtml?exportType=depositDraw&drawType='
							+ drawType + '&drawState=' + drawState
							+ '&startDate=' + startDate + '&endDate=' + endDate);
		}

		function f_using() {
			var selecteds = grid.getSelecteds();
			if (selecteds.length > 0) {
				var arr = [];
				for (var i = 0; i < selecteds.length; i++) {
					arr.push(selecteds[i].id);
				}
				LG.ajax({
					url : base + '/admin/member/using.jhtml',
					loading : '正在操作中...',
					data : {
						ids : arr
					},
					success : function() {
						LG.showSuccess('操作成功');
						f_reload();
					},
					error : function(message) {
						LG.showError(message);
					}
				});
			} else {
				LG.tip('请选择行!');
			}
		}
		function toremakecard() {
			$.ligerDialog.open({
				title : "补卡验证",
				height : 600,
				width : 900,
				url : base + '/admin/member/toRemakeCard.jhtml'
			});
		}
		function look(id) {
			$.ligerDialog
					.open({
						title : "快递单",
						height : 600,
						width : 900,
						url : base
								+ '/admin/deliveryTemplate/showTemplate.jhtml?memberId='
								+ id
					});
		}
		function looklog(id, name) {
			top.f_addTab(null, '用户【' + name + '】的补卡变更记录', base
					+ '/admin/memberlog/list.jhtml?id=' + id);
		}
	</script>
</head>
<body style="padding: 10px; overflow: hidden;">
	<div id="functions">
		<div class="clearfix">
			<div style="float: left;">
				<span id="searchDefault"> 会员信息(可用邮箱,手机,卡号,用户名):<input
					type="text" style="width: 100px; height: 20px;"
					name="search_EQ_memberDatas" />&nbsp;
				</span> <input type="button" id="search" class="button" value="搜索" /> <a
					href="#" id="search_btn_reset" face="Wingdings 3">清除本次搜索</a>
			</div>
		</div>
	</div>
	<div id="maingrid" style="margin: 0; padding: 0"></div>
</body>
</html>
