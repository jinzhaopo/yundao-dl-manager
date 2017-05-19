<#macro revertValue
	list headerKey="" headerValue="" listKey="" listValue="" valueList=[] value="" defaultValue=""
	>
<#if list?is_sequence>
	<#if listKey!="" && listValue!="">
		<#local hasValue=true>
		<#list list as item>
			<#local rkey=item[listKey]>
			<#local rvalue=item[listValue]>
			<#if value!="" && value == rkey>
				${rvalue}
				<#local hasValue=false>
				<#break>
			</#if>
		</#list>
		<#if defaultValue != "" && hasValue == true>
			${defaultValue}
		</#if>
	<#else>
		<#list list as item>
			<#local rkey=item>
			<#local rvalue=item>
			${rvalue}
		</#list>
	</#if>
<#else>
	<#list list?keys as key>
		<#local rkey=key/>
		<#local rvalue=list[key]/>
		<#if value!="" && value == rkey>${rvalue}</#if>
	</#list>
</#if>
</#macro>