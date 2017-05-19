<#macro tree menuTree target="mainFrame">
	<#if menuTree?? && menuTree?size gt 0>
	<dl>
		<#list menuTree as entry>
			<dt><span>${entry.menuName!''}</span></dt>
			<#if entry.subMenuList?? && entry.subMenuList?size gt 0>
				<#list entry.subMenuList as subMenu>
					<#if subMenu.action??>
						<dd><a target=${target} href="${base}/${subMenu.action.actionName!''}">${subMenu.menuName!''}</a> 
						<@tree menuTree=subMenu.subMenuList target=target />
						</dd>
					</#if>
				</#list>
			</#if>
		</#list> 
	</dl>	
	</#if>
</#macro>