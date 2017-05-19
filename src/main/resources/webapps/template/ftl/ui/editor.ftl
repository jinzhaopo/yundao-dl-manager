<#--
<textarea name="textarea"></textarea>
-->
<#macro editor
	fckName="" name="" value="" height="200" noValue="false"
	fullPage="false" toolbarSet="mydefault" class=""
	label="" noHeight="false" required="false" colspan="" width="100"
	>
<#if fckName=="">
<#local fckName=name?replace('.','_')>
</#if>
<script type="text/javascript">
var _fck_${fckName} = new FCKeditor('${name}');
_fck_${fckName}.BasePath = '${base}/core_res/thirdparty/fckeditor/';
_fck_${fckName}.Config["CustomConfigurationsPath"]="${base}/core_res/thirdparty/fckeditor/myconfig.js?d="+(new Date()*1);
<#--
_fck_${fckName}.Config["CustomConfigurationsPath"]="${base}/core_res/thirdparty/fckeditor/myconfig.js";
-->
_fck_${fckName}.Config["ImageBrowser"] = false ;
_fck_${fckName}.Config["FlashBrowser"] = false ;
_fck_${fckName}.Config["LinkBrowser"] = false ;
_fck_${fckName}.Config["MediaBrowser"] = false ;
_fck_${fckName}.ToolbarSet='${toolbarSet}';
_fck_${fckName}.Height=${height};
<#if fullPage=="true">
_fck_${fckName}.Config["FullPage"]=true;
</#if>
_fck_${fckName}.Value="<#if value!="">${value?js_string}<#elseif noValue!="true">${(name?eval)!?js_string}</#if>";
_fck_${fckName}.Create();
</script>
</#macro>