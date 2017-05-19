<#--
<select><option></option></select>
-->
<#macro select_map
	list value="" multiple="" headerKey="" headerValue="" listKey="" listValue="" headerButtom="false"
	label="" noHeight="false" noValue="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	id="" name="" class="" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#--
<#include "control.ftl"/><#rt/>
-->
<select<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if multiple!=""> multiple="${multiple}"</#if><#rt/>
<#include "/ftl/ui/common-attributes.ftl"/><#rt/>
<#include "/ftl/ui/scripting-events.ftl"/><#rt/>
/><#rt/>
<#if headerButtom=="false">
<#if headerKey!="" || headerValue!="">
	<option value="${headerKey}"<#if noValue!="false" && headerKey == (name?eval)!?string> selected="selected"</#if>>${headerValue}</option><#t/>
</#if>
</#if>
<#if list?is_sequence>
	<#if listKey!="" && listValue!="">
		<#list list as item>
			<option value="${item[listKey]}"<#if item[listKey]?string==value?string> selected="selected"</#if>>${item[listValue]!}</option><#t/>
		</#list>
	<#else>
		<#list list as item>
			<option value="${item}"<#if item==value> selected="selected"</#if>>${item}</option><#t/>
		</#list>		
	</#if>
<#else>
	<#--
	<#list list?keys as key>
		<option value="${key}"<#if key==value?string> selected="selected"</#if>>${list[key]}</option><#t/>
	</#list>
	-->
	<#list list.keySet() as key>
		<option value="${key}"<#if (value!="" && value==key) || (value=="" && noValue=="false" && name!="" && key==(name?eval)!?string)> selected="selected"</#if>>${list.get(key)}</option><#t/>
	</#list>
</#if>
<#if headerButtom!="false">
<#if headerKey!="" || headerValue!="">
	<option value="${headerKey}"<#if noValue!="false" && headerKey == (name?eval)!?string> selected="selected"</#if>>${headerValue}</option><#t/>
</#if>
</#if>
</select>
<#include "/ftl/ui/control-close.ftl"/><#rt/>
</#macro>
