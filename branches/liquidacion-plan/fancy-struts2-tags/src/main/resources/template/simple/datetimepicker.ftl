<input type="hidden"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
 id="${parameters.name?default("")?html}_text"<#rt/>
/>
<input type="text"<#rt/>
 name="${parameters.name?default("")?html}_day"<#rt/>
 id="${parameters.name?default("")?html}_day"<#rt/>
 maxlength="${parameters.get("maxlength")?default("2")?html}"<#rt/>
 size="${parameters.get("size")?default("2")?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.changefunc??>
 onchange="javascript:${parameters.changefunc}('${parameters.name?default("")?html}', this);"<#rt/>
</#if>
/>
<input type="text"<#rt/>
 name="${parameters.name?default("")?html}_month"<#rt/>
 id="${parameters.name?default("")?html}_month"<#rt/>
 maxlength="${parameters.get("maxlength")?default("2")?html}"<#rt/>
 size="${parameters.get("size")?default("2")?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.changefunc??>
 onchange="javascript:${parameters.changefunc}('${parameters.name?default("")?html}', this);"<#rt/>
</#if>
/>
<input type="text"<#rt/>
 name="${parameters.name?default("")?html}_year"<#rt/>
 id="${parameters.name?default("")?html}_year"<#rt/>
 maxlength="${parameters.get("maxlength")?default("4")?html}"<#rt/>
 size="${parameters.get("size")?default("4")?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.changefunc??>
 onchange="javascript:${parameters.changefunc}('${parameters.name?default("")?html}', this);"<#rt/>
</#if>
/>
<a href="#"<#rt/>
<#if parameters.pickerfunc??>
 onclick="javascript:${parameters.pickerfunc}('${parameters.name?default("")?html}');"<#rt/>
</#if>
>
<img<#rt/>
 src="images/calendario.gif" />
</a>
