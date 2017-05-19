<label for="${id+'_'+name+'_'+index}">
<input type="checkbox"<#rt/>
 id="${id+'_'+name+'_'+index}" value="${rkey}"<#rt/>
<#if valueList?seq_contains(rkey)> checked="checked"</#if><#rt/>
<#include "/ftl/ui/common-attributes.ftl"/><#rt/>
<#include "/ftl/ui/scripting-events.ftl"/><#rt/>
/>&nbsp;&nbsp;${rvalue}</label>&nbsp;&nbsp<#if hasNext> </#if>