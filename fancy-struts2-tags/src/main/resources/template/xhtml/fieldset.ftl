<#include "/${parameters.templateDir}/xhtml/control-close.ftl" />
<fieldset <#rt/>
<#if parameters.cssClass??> class="${parameters.cssClass?html}"<#rt/>
</#if>
>
<#if parameters.legend??> <legend>${parameters.legend}</legend>
</#if>
<#-- TODO: restaurar parametros cssClass y cssStyle a valores del form -->
<#include "/${parameters.templateDir}/xhtml/control.ftl" />
