package io.tommy.kimsea.web.utils

import org.thymeleaf.extras.minify.engine.SimpleMinifierTemplateHandler

class SimpleMinifierTemplateHandlerEx : SimpleMinifierTemplateHandler() {

    override fun minifyInlineScript(value: String?): String? {
        var minified = minifyTextInternal(value)
        minified = minified.replace("/*<![CDATA[*/", "").replace("/*]]>*/", "")
        return minified
    }
}